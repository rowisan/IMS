/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.dao;

import com.rowi.lms.modle.transaction.*;
import com.rowi.lms.modle.dto.TransactionDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ROSHAN
 */
public class TransactionDAO {

    public static int doCommonTransaction(
            TransactionDTO transactionDto,
            Connection connection,
            String referanceNo
    ) throws SQLException {

        Transaction transaction = transactionDto.getTransaction();
        ArrayList<TransactionHistory> transHistory = transactionDto.getTransHistory();
        Payment payment = transactionDto.getPayment();
        ArrayList<PaymentInformation> paymentHistory = transactionDto.getPaymentHistory();
        ArrayList<AccountTrans> accountTranses = transactionDto.getAccountTranses();

        //Trensaction
        int transIndexNo = 0;
        String sql = "INSERT INTO transaction (transaction_date, transaction_type, referance_no, document_no, cpu, cost_code, cust_code, note, status, user) VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement st = connection.prepareStatement(sql, 1);
        st.setString(1, transaction.getTransactionDate().toString());
        st.setString(2, transaction.getTransactionType());
        st.setString(3, referanceNo);
        st.setString(4, transaction.getDocumentNo());
        st.setString(5, transaction.getLoan());
        st.setString(6, transaction.getCostCode());
        st.setString(7, transaction.getCustomer());
        st.setString(8, transaction.getNote());
        st.setString(9, transaction.getStatus());
        st.setString(10, transaction.getUser());
        st.executeUpdate();
        ResultSet resTrans;
        resTrans = st.getGeneratedKeys();
        if (resTrans.next()) {
            transIndexNo = resTrans.getInt(1);

            //Transaction_history
            for (TransactionHistory transactionHistory : transHistory) {
                sql = "INSERT INTO transaction_history (`transaction`, `action`, `user`, `note`) VALUES (?,?,?,?)";
                PreparedStatement st2 = connection.prepareStatement(sql);
                st2.setString(1, transIndexNo + "");
                st2.setString(2, transactionHistory.getAction());
                st2.setString(3, transactionHistory.getUser());
                st2.setString(4, transactionHistory.getNote());
                st2.executeUpdate();
            }

            //save Payment
            if (payment != null) {

                sql = "INSERT INTO payment (transaction_date, cost_code, customer, amount, transaction, transaction_type, status, user) VALUES (?,?,?,?,?,?,?,?)";
                PreparedStatement st1 = connection.prepareStatement(sql, 1);
                st1.setString(1, payment.getTransactionDate().toString());
                st1.setString(2, payment.getCostCode());
                st1.setString(3, payment.getCustomer());
                st1.setString(4, String.valueOf(payment.getAmount()));
                st1.setString(5, transIndexNo + "");
                st1.setString(6, payment.getTransactionType());
                st1.setString(7, payment.getStatus());
                st1.setString(8, payment.getUser());
                st1.executeUpdate();

                ResultSet resPayment;
                resPayment = st1.getGeneratedKeys();
                if (resPayment.next()) {

                    int paymentIndexNo = resPayment.getInt(1);

                    //Payment_history
                    for (PaymentInformation paymentInformation : paymentHistory) {
                        sql = "INSERT INTO payment_information (payment, payment_setting, amount, transaction, transaction_type, refarance_no) VALUES (?,?,?,?,?,?)";
                        PreparedStatement st2 = connection.prepareStatement(sql);
                        st2.setString(1, paymentIndexNo + "");
                        st2.setString(2, paymentInformation.getPaymentSetting());
                        st2.setString(3, String.valueOf(paymentInformation.getAmount()));
                        st2.setString(4, transIndexNo + "");
                        st2.setString(5, paymentInformation.getTransactionType());
                        st2.setString(6, referanceNo);
                        st2.executeUpdate();
                    }

                }

            }
            if (accountTranses != null) {

                //Account_transaction
                for (AccountTrans accountTrans : accountTranses) {
                    sql = "INSERT INTO account_trans (transaction_date, cost_code, account_setting, description, account, credit_amount, debit_amount, transaction, transaction_type, type, status, user) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement st3 = connection.prepareStatement(sql);
                    st3.setString(1, accountTrans.getTransactionDate().toString());
                    st3.setString(2, accountTrans.getCostCode());
                    st3.setString(3, accountTrans.getAccountSetting());
                    st3.setString(4, accountTrans.getDescription() + referanceNo);
                    st3.setString(5, accountTrans.getAccount());
                    st3.setString(6, String.valueOf(accountTrans.getCreditAmount()));
                    st3.setString(7, String.valueOf(accountTrans.getDebitAmount()));
                    st3.setString(8, transIndexNo + "");
                    st3.setString(9, accountTrans.getTransactionType());
                    st3.setString(10, accountTrans.getType());
                    st3.setString(11, accountTrans.getStatus());
                    st3.setString(12, accountTrans.getUser());
                    st3.executeUpdate();
                }

            }
        }
        return transIndexNo;
    }
}
