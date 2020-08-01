/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.modle.vdo;

import com.rowi.lms.modle.Account;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author ROSHAN
 */
public class VoucherVDO {

    private String customer;
    private String customerCode;
    private String customerName;
    private String cpu;
    private Date   voucherDate;
    private String documentNo;
    private String remark;
    private String payAmount;
    private String user;
    private String branch;
    private String voucherType;
    private String transactionType;
    private String status;
    private ArrayList<String> accounts;
    private ArrayList<PaymentMethodVDO> paymentMethodVDOs;

    public VoucherVDO() {
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<String> accounts) {
        this.accounts = accounts;
    }

    public ArrayList<PaymentMethodVDO> getPaymentMethodVDOs() {
        return paymentMethodVDOs;
    }

    public void setPaymentMethodVDOs(ArrayList<PaymentMethodVDO> paymentMethodVDOs) {
        this.paymentMethodVDOs = paymentMethodVDOs;
    }

}
