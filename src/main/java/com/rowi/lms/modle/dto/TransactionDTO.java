/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.modle.dto;

import com.rowi.lms.modle.transaction.AccountTrans;
import com.rowi.lms.modle.transaction.Payment;
import com.rowi.lms.modle.transaction.PaymentInformation;
import com.rowi.lms.modle.transaction.Transaction;
import com.rowi.lms.modle.transaction.TransactionHistory;
import java.util.ArrayList;

/**
 *
 * @author ROSHAN
 */
public class TransactionDTO {

    private Transaction transaction;
    private ArrayList<TransactionHistory> transHistory;
    private Payment payment;
    private ArrayList<PaymentInformation> paymentHistory;
    private ArrayList<AccountTrans> accountTranses;

    public TransactionDTO() {
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public ArrayList<TransactionHistory> getTransHistory() {
        return transHistory;
    }

    public void setTransHistory(ArrayList<TransactionHistory> transHistory) {
        this.transHistory = transHistory;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public ArrayList<PaymentInformation> getPaymentHistory() {
        return paymentHistory;
    }

    public void setPaymentHistory(ArrayList<PaymentInformation> paymentHistory) {
        this.paymentHistory = paymentHistory;
    }

    public ArrayList<AccountTrans> getAccountTranses() {
        return accountTranses;
    }

    public void setAccountTranses(ArrayList<AccountTrans> accountTranses) {
        this.accountTranses = accountTranses;
    }

}
