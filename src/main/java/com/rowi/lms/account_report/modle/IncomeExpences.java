/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.account_report.modle;

/**
 *
 * @author kandy
 */
public class IncomeExpences {

    private String fromDate;
    private String toDate;
    private String date;
    private String description;
    private String Dr;
    private String Cr;
    private String branch;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDr() {
        return Dr;
    }

    public void setDr(String Dr) {
        this.Dr = Dr;
    }

    public String getCr() {
        return Cr;
    }

    public void setCr(String Cr) {
        this.Cr = Cr;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
    
    
}
