/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.action.impl;

import com.rowi.lms.action.Action;
import com.rowi.lms.bindinglayer.VoucherMVB;
import com.rowi.lms.common.FormAction;
import com.rowi.lms.common.SessionVariables;
import com.rowi.lms.common.SystemMessage.CommonLogger;
import com.rowi.lms.common.TransactionType;
import com.rowi.lms.common.VoucherType;
import com.rowi.lms.common.status.VoucherStatus;
import com.rowi.lms.util.Utilitys;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import com.rowi.lms.common.system_setting.PaymentSetting;
import com.rowi.lms.modle.vdo.VoucherVDO;
import com.rowi.lms.modle.vdo.PaymentMethodVDO;

/**
 *
 * @author ROSHAN
 */
public class ReceiptOnAccountController extends Action {

    private String msg;

    @Override
    public void run() {
        String action = (String) request.getParameter("txtAction");
        System.out.println("Action : " + action);
        if (action.equals(FormAction.SAVE)) {
            save();
        } else if (action.equals(FormAction.UPDATE)) {
            update();
        } else if (action.equals(FormAction.SEARCH)) {
            search();
        } else if (action.equals(FormAction.DELETE)) {
            delete();
        } else if (action.equals(FormAction.HISTORY)) {
            history();
        }
    }

    @Override
    protected void save() {
        try {
            CommonLogger.log("DEBUG : ", ReceiptOnAccountController.class.getName(), "save() >>");

            String account = request.getParameter("txtAccount");
            ArrayList<String> accounts = new ArrayList<String>();
            accounts.add(account);

            Date voucherDate = Date.valueOf(Utilitys.systemDate());
            String branch = (String) session.getAttribute(SessionVariables.BRANCH);
            String docunetNo = request.getParameter("txtDocumentNo");
            String remark = request.getParameter("txtRemark");
            String narration = request.getParameter("txtNarration");
            double amount = Double.valueOf(request.getParameter("txtAmount"));
            String payTerm = "";
            String type = VoucherType.RECEIPT_ON_ACCOUNT;
            String transactionType = TransactionType.RECEIPT;
            String user = (String) session.getAttribute(SessionVariables.USER);
            String status = VoucherStatus.ACTIVE;

            //PaymentMethodVDO
            ArrayList<PaymentMethodVDO> paymentMethodVDOs = new ArrayList<PaymentMethodVDO>();
            PaymentMethodVDO paymentMethodVDO = new PaymentMethodVDO();
            paymentMethodVDO.setAmount(amount);
            paymentMethodVDO.setPaymentMethod("CASH");
            paymentMethodVDO.setPaymentSetting(PaymentSetting.RECEIPT_ON_ACCOUNT_CASH);
            paymentMethodVDOs.add(paymentMethodVDO);

            //ReceiptAgPackageVDO
            VoucherVDO voucherVDO = new VoucherVDO();
            voucherVDO.setVoucherDate(voucherDate);
            voucherVDO.setDocumentNo(docunetNo);
            voucherVDO.setRemark(narration + " : " + remark);
            voucherVDO.setPayAmount(String.valueOf(amount));
            voucherVDO.setUser(user);
            voucherVDO.setBranch(branch);
            voucherVDO.setVoucherType(type);
            voucherVDO.setTransactionType(transactionType);
            voucherVDO.setStatus(status);
            voucherVDO.setAccounts(accounts);
            voucherVDO.setPaymentMethodVDOs(paymentMethodVDOs);

            msg = VoucherMVB.receiptVoucher(voucherVDO);

            CommonLogger.log("DEBUG : ", ReceiptOnAccountController.class.getName(), "save() >> " + msg);
            request.setAttribute(SessionVariables.MSG, msg);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ReceiptAgAccountServlet?FUNC=" + FormAction.SAVE);
            dispatcher.forward(request, response);
        } catch (ServletException ex) {
            CommonLogger.handle(ex, ReceiptOnAccountController.class.getName(), "save()");
            Logger.getLogger(ReceiptOnAccountController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            CommonLogger.handle(ex, ReceiptOnAccountController.class.getName(), "save()");
            Logger.getLogger(ReceiptOnAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void search() {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ReceiptAgAccountServlet?FUNC=" + FormAction.SEARCH);
            dispatcher.forward(request, response);
        } catch (ServletException ex) {
            CommonLogger.handle(ex, ReceiptOnAccountController.class.getName(), "save()");
            Logger.getLogger(ReceiptOnAccountController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            CommonLogger.handle(ex, ReceiptOnAccountController.class.getName(), "save()");
            Logger.getLogger(ReceiptOnAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void getList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void history() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
