/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.modle;

/**
 *
 * @author ROSHAN
 */
public class Settlement {

    private int indexNo;
    private String customerPackageUnit;
    private String charge;
    private String referanceNo;
    private String refType;
    private double amount;
    private double privSettle;
    private String privSettleRef;
    private double paid;
    private double balance;
    private double minExam;
    private double minTrial;
    private String status;
    private int printOrder;
    private String description;
    private String remark;

    public int getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(int indexNo) {
        this.indexNo = indexNo;
    }

    public String getCustomerPackageUnit() {
        return customerPackageUnit;
    }

    public void setCustomerPackageUnit(String customerPackageUnit) {
        this.customerPackageUnit = customerPackageUnit;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getReferanceNo() {
        return referanceNo;
    }

    public void setReferanceNo(String referanceNo) {
        this.referanceNo = referanceNo;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrivSettle() {
        return privSettle;
    }

    public void setPrivSettle(double privSettle) {
        this.privSettle = privSettle;
    }

    public String getPrivSettleRef() {
        return privSettleRef;
    }

    public void setPrivSettleRef(String privSettleRef) {
        this.privSettleRef = privSettleRef;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getMinExam() {
        return minExam;
    }

    public void setMinExam(double minExam) {
        this.minExam = minExam;
    }

    public double getMinTrial() {
        return minTrial;
    }

    public void setMinTrial(double minTrial) {
        this.minTrial = minTrial;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrintOrder() {
        return printOrder;
    }

    public void setPrintOrder(int printOrder) {
        this.printOrder = printOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
