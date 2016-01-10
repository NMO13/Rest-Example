/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.number26;
/**
 *
 * @author Martin
 */
public class Transaction {
    double amount;
    String type;
    long parentId;
    long transactionId;
    Transaction(double amount, String type, long parentId, long transactionId) {
        this.amount = amount;
        this.type = type;
        this.parentId = parentId;
        this.transactionId = transactionId;
    }

    String getType() {
        return type;
    }
}
