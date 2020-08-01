/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.services;

import com.rowi.lms.modle.Account;
import com.rowi.lms.util.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ROSHAN
 */
public class AccountSER {

    public static ArrayList<Account> getAccounts(String type) {
        ArrayList<Account> accounts = new ArrayList<Account>();
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select * from account where account.`type` in (" + type + ") and account.`status` = 'ACTIVE'";

            Statement statement = (Statement) connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Account account = new Account();
                account.setAccountCode(resultSet.getString("code"));
                account.setAccountName(resultSet.getString("name"));
                accounts.add(account);
            }
            DBConnection.releasConnection(connection);
        } catch (SQLException ex) {
            DBConnection.releasConnection(connection);
            Logger.getLogger(AccountSER.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accounts;

    }
}
