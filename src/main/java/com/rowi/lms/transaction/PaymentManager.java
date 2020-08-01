/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.transaction;

import com.rowi.lms.modle.vdo.PaymentMethodVDO;
import com.rowi.lms.modle.vdo.VoucherVDO;
import com.rowi.lms.modle.transaction.Payment;
import com.rowi.lms.modle.transaction.PaymentInformation;
import com.rowi.lms.util.Utilitys;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author ROSHAN
 */
public class PaymentManager {

    public static Payment getPayment(VoucherVDO voucherVDO, String status) {

        String cpu = voucherVDO.getCpu();
        String customer = voucherVDO.getCustomer();
        Date voucherDate = Date.valueOf(Utilitys.systemDate());
        String branch = voucherVDO.getBranch();
        String amount = voucherVDO.getPayAmount();
        String user = voucherVDO.getUser();
        String transactionType = voucherVDO.getTransactionType();

        //Payment payment
        Payment payment = new Payment();
        payment.setTransactionDate(voucherDate);
        payment.setCostCode(branch);
        payment.setCustomer(customer);
        payment.setCashireSession(0);
        payment.setAmount(Double.parseDouble(amount));
        payment.setTransaction(0);
        payment.setTransactionType(transactionType);
        payment.setStatus(status);
        payment.setUser(user);

        return payment;
    }

    public static ArrayList<PaymentInformation> getPaymantHistory(VoucherVDO voucherVDO) {
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
        ArrayList<PaymentMethodVDO> paymentMethodVDOs = voucherVDO.getPaymentMethodVDOs();

        //ArrayList<PaymentInformation> paymentHistory
        ArrayList<PaymentInformation> paymentHistory = new ArrayList<PaymentInformation>();

        for (PaymentMethodVDO paymentMethodVDO : paymentMethodVDOs) {
            PaymentInformation paymentInformation = new PaymentInformation();
            paymentInformation.setPaymentSetting(paymentMethodVDO.getPaymentSetting());
            paymentInformation.setAmount(paymentMethodVDO.getAmount());
            paymentInformation.setTransaction(0);
            paymentInformation.setTransactionType(transactionType);
            paymentInformation.setRefaranceNo(paymentMethodVDO.getReferenceNo());
            paymentHistory.add(paymentInformation);
        }

        return paymentHistory;
    }

}
