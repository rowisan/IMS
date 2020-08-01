/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.bindinglayer;

import com.rowi.lms.common.status.TransactionStatus;
import com.rowi.lms.modle.Voucher;
import com.rowi.lms.modle.dto.TransactionDTO;
import com.rowi.lms.modle.transaction.AccountTrans;
import com.rowi.lms.modle.transaction.Payment;
import com.rowi.lms.modle.transaction.PaymentInformation;
import com.rowi.lms.modle.transaction.Transaction;
import com.rowi.lms.modle.transaction.TransactionHistory;
import com.rowi.lms.modle.vdo.PaymentMethodVDO;
import com.rowi.lms.modle.vdo.VoucherVDO;
import com.rowi.lms.services.VoucherSER;
import com.rowi.lms.transaction.AccountTransactionManager;
import com.rowi.lms.transaction.PaymentManager;
import com.rowi.lms.transaction.TransactionManager;
import java.util.ArrayList;

/**
 *
 * @author ROSHAN
 */
public class VoucherMVB {

    private static String msg;

    public static String receiptVoucher(VoucherVDO voucherVDO) {

        //Voucher voucher
        Voucher voucher = new Voucher();
        
        voucher.setCpu(voucherVDO.getCpu());
        voucher.setCustomer(voucherVDO.getCustomer());
        voucher.setCode(voucherVDO.getCustomerCode());
        voucher.setCustomerName(voucherVDO.getCustomerName());
        voucher.setVoucherDate(voucherVDO.getVoucherDate());
        voucher.setBranch(voucherVDO.getBranch());
        voucher.setDocumentNo(voucherVDO.getDocumentNo());
        voucher.setRemark(voucherVDO.getRemark());
        voucher.setAmount(voucherVDO.getPayAmount());
        voucher.setPayTerm("");
        voucher.setType(voucherVDO.getVoucherType());
        voucher.setUser(voucherVDO.getUser());
        voucher.setStatus(voucherVDO.getStatus());

        //Transaction transaction
        Transaction transaction = TransactionManager.getTransaction(voucherVDO, TransactionStatus.ACTIVE);

        //ArrayList<TransactionHistory> transHistory
        ArrayList<TransactionHistory> transactionHistorys = TransactionManager.getTransactionHistory(voucherVDO, "INSERT");

        //Payment payment
        Payment payment = PaymentManager.getPayment(voucherVDO, TransactionStatus.ACTIVE);

        //ArrayList<PaymentInformation> paymentHistory
        ArrayList<PaymentInformation> paymentHistory = PaymentManager.getPaymantHistory(voucherVDO);

        //ArrayList<AccountTrans> accountTranses
        ArrayList<AccountTrans> accountTranses = AccountTransactionManager.voucherTransaction(voucherVDO);

        //TransactionDTO
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransaction(transaction);
        transactionDTO.setTransHistory(transactionHistorys);
        transactionDTO.setPayment(payment);
        transactionDTO.setPaymentHistory(paymentHistory);
        transactionDTO.setAccountTranses(accountTranses);

        msg = VoucherSER.save(voucher, transactionDTO, voucherVDO);
        return msg;
    }

    public static String paymentVoucher(VoucherVDO voucherVDO, ArrayList<PaymentMethodVDO> paymentMethodVDOs) {
        return null;
    }
}
