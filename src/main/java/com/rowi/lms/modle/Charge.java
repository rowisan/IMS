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
public class Charge {

    private String code;
    private String description;
    private String chargeType;
    private double defaultAmount;
    private double maxDiscount;
    private double minExam;
    private double minTrial;
    private String chargeRebit;
    private double amount;
    private String remark;
    private String printOrder;
    private String cpu;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public double getDefaultAmount() {
        return defaultAmount;
    }

    public void setDefaultAmount(double defaultAmount) {
        this.defaultAmount = defaultAmount;
    }

    public double getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(double maxDiscount) {
        this.maxDiscount = maxDiscount;
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

    public String getChargeRebit() {
        return chargeRebit;
    }

    public void setChargeRebit(String chargeRebit) {
        this.chargeRebit = chargeRebit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPrintOrder() {
        return printOrder;
    }

    public void setPrintOrder(String printOrder) {
        this.printOrder = printOrder;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

}
