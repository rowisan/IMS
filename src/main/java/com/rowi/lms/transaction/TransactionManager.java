/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.transaction;

import com.rowi.lms.common.TransactionType;
import com.rowi.lms.modle.vdo.VoucherVDO;
import com.rowi.lms.modle.transaction.Transaction;
import com.rowi.lms.modle.transaction.TransactionHistory;
import com.rowi.lms.modle.vdo.OtherChargeVDO;
import com.rowi.lms.util.Utilitys;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author ROSHAN
 */
public class TransactionManager {

    public static Transaction getTransaction(VoucherVDO voucherVDO, String status) {

        String cpu = voucherVDO.getCpu();
        String customer = voucherVDO.getCustomer();
        Date voucherDate = Date.valueOf(Utilitys.systemDate());
        String branch = voucherVDO.getBranch();
        String docunetNo = voucherVDO.getDocumentNo();
        String remark = voucherVDO.getRemark();
        String amount = voucherVDO.getPayAmount();
        String payTerm = "";
        String transactionType = voucherVDO.getTransactionType();
        String user = voucherVDO.getUser();

        Transaction transaction = new Transaction();
        transaction.setTransactionDate(voucherDate);
        transaction.setTransactionType(transactionType);
        transaction.setReferanceNo("");
        transaction.setDocumentNo(docunetNo);
        transaction.setLoan(cpu);
        transaction.setCostCode(branch);
        transaction.setCustomer(customer);
        transaction.setNote(remark);
        transaction.setStatus(status);
        transaction.setUser(user);

        return transaction;
    }

    public static ArrayList<TransactionHistory> getTransactionHistory(VoucherVDO receiptAgPackageVDO, String action) {
        String user = receiptAgPackageVDO.getUser();
        String remark = receiptAgPackageVDO.getRemark();
        //ArrayList<TransactionHistory> transHFistory
        ArrayList<TransactionHistory> transactionHistorys = new ArrayList<TransactionHistory>();
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setAction(action);
        transactionHistory.setUser(user);
        transactionHistory.setNote(remark);
        transactionHistorys.add(transactionHistory);
        return transactionHistorys;
    }

    public static Transaction getTransaction(OtherChargeVDO OtherChargeVDO, String status) {
        String cpu = OtherChargeVDO.getCpu();
        String customer = OtherChargeVDO.getCustomer();
        Date voucherDate = Date.valueOf(Utilitys.systemDate());
        String branch = OtherChargeVDO.getBranch();
        String docunetNo = "";
        String remark = OtherChargeVDO.getRemark();
        String amount = OtherChargeVDO.getAmount();
        String payTerm = "";
        String transactionType = TransactionType.OTHER_CHARGE;
        String user = OtherChargeVDO.getUser();

        Transaction transaction = new Transaction();
        transaction.setTransactionDate(voucherDate);
        transaction.setTransactionType(transactionType);
        transaction.setReferanceNo("");
        transaction.setDocumentNo(docunetNo);
        transaction.setLoan(cpu);
        transaction.setCostCode(branch);
        transaction.setCustomer(customer);
        transaction.setNote(remark);
        transaction.setStatus(status);
        transaction.setUser(user);

        return transaction;
    }
    
    public static ArrayList<TransactionHistory> getTransactionHistory(OtherChargeVDO OtherChargeVDO, String action) {
        String user = OtherChargeVDO.getUser();
        String remark = OtherChargeVDO.getRemark();
        //ArrayList<TransactionHistory> transHFistory
        ArrayList<TransactionHistory> transactionHistorys = new ArrayList<TransactionHistory>();
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setAction(action);
        transactionHistory.setUser(user);
        transactionHistory.setNote(remark);
        transactionHistorys.add(transactionHistory);
        return transactionHistorys;
    }

}
