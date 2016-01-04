/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
 /*
 * @author Martin
 * Tests the RESTful service methods in a simple way. In a real world project, I would use unit tests instead.
 * But for this simple example, it should be good enough.
 */
public class SimpleTest {
    private static final String USER_AGENT = "Mozilla/5.0";
    public static void main(String[] args) throws MalformedURLException, IOException {
  
                put(1, 90, "a", -1);
                
                put(2, 90, "b", 1);
                
                put(3, 90, "a", 1);
                
                put(8, 90, "b", -1);
                
                put(9, 90, "c", 2);
                
                put(14, 90, "c", 3);
                
                put(10, 90, "c", 14);
                
                getCommonTransactions(1);
                
                getCommonTransactions(-1);
                
                put(34, 0, "a", 11);
                
                put(11, 0, "a", 34);
                
                put(12, 543, "a", 9);
                
                
                
                getTransaction("a");
                
                getTransaction("b");
                
                getTransaction("d");
                
                getSum(-1);
                
                getSum(1);
        
    }
    
    private static void put(long transactionId, double amount, String type, long parentId) throws MalformedURLException, IOException {
        StringBuilder url = new StringBuilder();
        url.append("http://localhost:8080/CodeChallenge/rest/TransactionService/transaction/");
        url.append(transactionId);
        
        StringBuilder urlParameters  = new StringBuilder();
        urlParameters.append("amount=");
        urlParameters.append(amount);
        urlParameters.append("&");
        
        urlParameters.append("type=");
        urlParameters.append(type);
        urlParameters.append("&");
        
        if(parentId >= 0) {
            urlParameters.append("parent_id=");
            urlParameters.append(parentId);
        }

        byte[] postData = urlParameters.toString().getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        
        URL obj = new URL(url.toString());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty( "charset", "utf-8");
        con.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
        con.setUseCaches( false );
        // optional default is GET
        con.setRequestMethod("PUT");
        con.setDoOutput(true);
        try( DataOutputStream wr = new DataOutputStream( con.getOutputStream())) {
            wr.write( postData );
         }
        
        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);
        //TODO return result JSON and assert it
    }
    
    private static void getTransaction(String type) throws MalformedURLException, IOException {
        StringBuilder url = new StringBuilder();
        url.append("http://localhost:8080/CodeChallenge/rest/TransactionService/types/");
        url.append(type);
		
        URL obj = new URL(url.toString());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty( "charset", "utf-8");
        con.setUseCaches( false );
        // optional default is GET
        con.setRequestMethod("GET");
        
        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);
        //TODO return result JSON and assert it
    }
    
    private static void getCommonTransactions(long transactionId) throws MalformedURLException, ProtocolException, IOException {
        StringBuilder url = new StringBuilder();
        url.append("http://localhost:8080/CodeChallenge/rest/TransactionService/transaction/");
        url.append(transactionId);
		
        URL obj = new URL(url.toString());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty( "charset", "utf-8");
        con.setUseCaches( false );
        // optional default is GET
        con.setRequestMethod("GET");
        
        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);
        //TODO return result JSON and assert it
    }
    
        private static void getSum(long transactionId) throws MalformedURLException, ProtocolException, IOException {
        StringBuilder url = new StringBuilder();
        url.append("http://localhost:8080/CodeChallenge/rest/TransactionService/sum/");
        url.append(transactionId);
		
        URL obj = new URL(url.toString());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty( "charset", "utf-8");
        con.setUseCaches( false );
        // optional default is GET
        con.setRequestMethod("GET");
        
        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);
        //TODO return result JSON and assert it
    }
}
