/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.number26;
import java.util.ArrayList;
import java.util.Stack;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Martin
 */
@Path("/TransactionService")
public class TransactionService {
    private static final TransactionDao transactionDao = new TransactionDao();
    @GET
    @Path("/transaction/{transaction_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTransaction(@PathParam("transaction_id") long transaction_id){
        Transaction ta = transactionDao.getTransaction(transaction_id);
        if(ta == null) 
            return null;
        return JSONBuilder.build(ta);
    }

    @PUT
    @Path("/transaction/{transaction_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String createTransaction(@PathParam("transaction_id") long transaction_id,
            @FormParam("amount") double amount,
            @FormParam("type") String type,
            @FormParam("parent_id") @DefaultValue("-1") long parent_id) {
       if(transactionDao.addTransaction(new Transaction(amount, type, parent_id, transaction_id)))
        return JSONBuilder.ok();
       return JSONBuilder.failure();
    }
    
    @GET
    @Path("/types/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTransactionsByType(@PathParam("type") String type)
    {
        return JSONBuilder.getIds(transactionDao.getTransactions(), type);
    }
    
    @GET
    @Path("/sum/{transaction_id}")
    @Produces(MediaType.APPLICATION_JSON)
    /*
    DFS graph searching algorithm to find all transaction which are transitively dependent
    Note that a tuple list is used to mark those transactions, which are already visited in order to break cycles
    */
    public String getSum(@PathParam("transaction_id") long transaction_id) throws Exception
    {
        // a tuple list is used to mark those transactions, which are already visited
        Stack<Tuple<Transaction, Boolean>> buffer = new Stack<>();
        final ArrayList<Tuple<Transaction, Boolean>> visitedList = new ArrayList<>();
        for(Object t0 : transactionDao.getTransactions()) {
            Tuple tupleNew = new Tuple(t0, false);
            visitedList.add(tupleNew);
            if(transaction_id == ((Transaction)t0).transactionId)
            {
                buffer.push(tupleNew);
                tupleNew.second = false;
            }
        }
        if(buffer.size() < 1)
            return null;
        if(buffer.size() > 1)
            throw new Exception("Two transactions with the same id exist");
        double sum = 0.0;
        do {
            Tuple tuple = buffer.pop();
            Transaction t = (Transaction)tuple.first;
            sum += t.amount;            
            visitedList.stream().forEach((tupleTemp) -> {
                Transaction t1 = (Transaction) tupleTemp.first;
                if (t1.parentId == t.transactionId && tupleTemp.second == false) {
                    buffer.push(tupleTemp);
                    tupleTemp.second = true;
                }
            });
            
        } while(!buffer.empty());
        return JSONBuilder.getSum(sum);
    }
    
    class Tuple<U, V> {
        Tuple(U u, V v) {
            first = u;
            second = v;
        }
        U first;
        V second;
    }
}
