/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.account_report.services;

import java.util.ArrayList;
import com.rowi.lms.account_report.dao.IncomeExpencesDAO;
import com.rowi.lms.account_report.modle.IncomeExpences;

/**
 *
 * @author kandy
 */
public class IncomeExpencesSER {

    public static ArrayList<IncomeExpences> getList(String fromDate, String toDate, String branch) {
         return IncomeExpencesDAO.getList(fromDate, toDate, branch);
    }

}
