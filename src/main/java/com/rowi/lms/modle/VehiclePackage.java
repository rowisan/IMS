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
public class VehiclePackage {
    private String code;
    private String description;
    private String customer_package_unit;
    private double amount;
    private double minForTrial;
    private double minForExam;

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

    public String getCustomer_package_unit() {
        return customer_package_unit;
    }

    public void setCustomer_package_unit(String customer_package_unit) {
        this.customer_package_unit = customer_package_unit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getMinForTrial() {
        return minForTrial;
    }

    public void setMinForTrial(double minForTrial) {
        this.minForTrial = minForTrial;
    }

    public double getMinForExam() {
        return minForExam;
    }

    public void setMinForExam(double minForExam) {
        this.minForExam = minForExam;
    }

}
