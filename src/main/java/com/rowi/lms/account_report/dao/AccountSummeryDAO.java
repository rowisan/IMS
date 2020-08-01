/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.account_report.dao;

import com.rowi.lms.account_report.modle.AccountSummery;
import com.rowi.lms.common.SystemMessage.CommonLogger;
import com.rowi.lms.util.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.rowi.lms.util.Utilitys;

/**
 *
 * @author kandy
 */
public class AccountSummeryDAO {

    public static ArrayList<AccountSummery> getList(String fromDate, String toDate, String account) {
        Connection connection = DBConnection.getConnection();
        ArrayList<AccountSummery> accountSummerys = new ArrayList<AccountSummery>();
        String qry = "select * from account_trans "
                + "left join transaction on transaction.index_no = account_trans.`transaction`"
                + "where account_trans.account = '"+ account +"' and account_trans.`status` = 'ACTIVE' and account_trans.transaction_date BETWEEN '" + fromDate + "' AND '" + toDate + "'";
        Statement statement;
        try {
            statement = (Statement) connection.createStatement();
            ResultSet resultSet = statement.executeQuery(qry);

            while (resultSet.next()) {
                AccountSummery accountSummery = new AccountSummery();
                accountSummery.setAccount(resultSet.getString("account"));
                accountSummery.setDate(resultSet.getString("account_trans.transaction_date"));
                String cr = resultSet.getString("credit_amount");
                cr = Utilitys.getDecimalValue(cr);
                accountSummery.setCr(cr);
                
                String dr = resultSet.getString("debit_amount");
                dr = Utilitys.getDecimalValue(dr);
                accountSummery.setDr(dr);

                String description = resultSet.getString("note");
                accountSummery.setDescription(description);
                accountSummerys.add(accountSummery);
            }

        } catch (Exception e) {
            CommonLogger.handle(e, AccountSummeryDAO.class.getName(), "getList()");
            Logger.getLogger(AccountSummeryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        DBConnection.releasConnection(connection);
        return accountSummerys;
    }

}
