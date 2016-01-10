/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.number26;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author Martin
 */
public class TransactionDao {

    // mimics a persistent storage medium like a database
    private Hashtable<Long, Transaction> transactionList = new Hashtable<>();
    
    Transaction getTransaction(Long transactionId) {
        if(transactionId < 0)
            return null;
        return transactionList.get(transactionId);
    }

    boolean addTransaction(Transaction transaction) {
        if(transactionList.containsKey(transaction.transactionId))
            return false;
        // if a parent id has been specified but is not present in the list, then return false
        if(transaction.parentId >= 0 && !transactionList.containsKey(transaction.parentId))
            return false;
        if(transaction.parentId == transaction.transactionId)
            return false;
        transactionList.put(transaction.transactionId, transaction);
        return true;
    }
    
    Object[] getTransactions() {
        return transactionList.values().toArray();
    }
    
}
