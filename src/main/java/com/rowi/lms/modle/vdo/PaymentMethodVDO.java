/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.modle.vdo;

/**
 *
 * @author ROSHAN
 */
public class PaymentMethodVDO {

    private String paymentMethod;
    private double amount;
    private String paymentSetting;
    private String referenceNo;
    private String recoverDate;
    private String bank;

    public PaymentMethodVDO() {
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentSetting() {
        return paymentSetting;
    }

    public void setPaymentSetting(String paymentSetting) {
        this.paymentSetting = paymentSetting;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getRecoverDate() {
        return recoverDate;
    }

    public void setRecoverDate(String recoverDate) {
        this.recoverDate = recoverDate;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

}
