/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.account_report.services;

import com.rowi.lms.account_report.dao.AccountSummeryDAO;
import com.rowi.lms.account_report.modle.AccountSummery;
import java.util.ArrayList;

/**
 *
 * @author kandy
 */
public class AccountSummerySER {

    public static ArrayList<AccountSummery> getList(String fromDate, String toDate, String account) {
         return AccountSummeryDAO.getList(fromDate, toDate, account);
    }

}
