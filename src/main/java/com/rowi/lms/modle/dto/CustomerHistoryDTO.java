/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.modle.dto;

/**
 *
 * @author kandy
 */
public class CustomerHistoryDTO {

    private String code;
    private String name;
    private String nic;
    private String phone;
    private String packkage;
    private String description;

    private double totalAmount;
    private double paidAmount;
    private double minOfExam;
    private double minOfTrial;

    private String examStatus;
    private String trialStatus;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPackkage() {
        return packkage;
    }

    public void setPackkage(String packkage) {
        this.packkage = packkage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getMinOfExam() {
        return minOfExam;
    }

    public void setMinOfExam(double minOfExam) {
        this.minOfExam = minOfExam;
    }

    public double getMinOfTrial() {
        return minOfTrial;
    }

    public void setMinOfTrial(double minOfTrial) {
        this.minOfTrial = minOfTrial;
    }

    public String getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(String examStatus) {
        this.examStatus = examStatus;
    }

    public String getTrialStatus() {
        return trialStatus;
    }

    public void setTrialStatus(String trialStatus) {
        this.trialStatus = trialStatus;
    }

}
