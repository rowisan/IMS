/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.account_report.dao;

import com.rowi.lms.common.SystemMessage.CommonLogger;
import com.rowi.lms.util.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.rowi.lms.account_report.modle.IncomeExpences;
import com.rowi.lms.util.Utilitys;

/**
 *
 * @author kandy
 */
public class IncomeExpencesDAO {

    public static ArrayList<IncomeExpences> getList(String fromDate, String toDate, String branch) {
        Connection connection = DBConnection.getConnection();
        ArrayList<IncomeExpences> incomeExpenceses = new ArrayList<IncomeExpences>();
        String qry = "SELECT voucher.voucher_date, voucher.amount, voucher.type, voucher.remark, voucher.document_no, customer.code FROM voucher LEFT JOIN customer ON customer.index_no = voucher.customer WHERE voucher_date BETWEEN '" + fromDate + "' AND '" + toDate + "' AND voucher.branch = '" + branch + "'";
        Statement statement;
        try {
            statement = (Statement) connection.createStatement();
            ResultSet resultSet = statement.executeQuery(qry);

            while (resultSet.next()) {
                IncomeExpences incomeExpences = new IncomeExpences();
                incomeExpences.setDate(resultSet.getString("voucher_date"));
                String amount = resultSet.getString("amount");
                amount = Utilitys.getDecimalValue(amount);
                String type = resultSet.getString("type");
                String remark = resultSet.getString("remark");
                String customer = resultSet.getString("code");
                String doc = resultSet.getString("document_no");

                if (customer != null) {
                    customer = "Customer : " + customer + " | ";
                } else {
                    customer = "";
                }

                if (doc != null) {
                    doc = "Doc No :" + doc + " | ";
                } else {
                    doc = "";
                }

                String description = customer + doc + remark;

                if (type.startsWith("RECEIPT")) {
                    incomeExpences.setDr(amount);
                    incomeExpences.setCr("");
                } else if (type.startsWith("VOUCHER")) {
                    incomeExpences.setDr("");
                    incomeExpences.setCr(amount);
                }

                incomeExpences.setDescription(description);
                incomeExpenceses.add(incomeExpences);
            }

        } catch (Exception e) {
            CommonLogger.handle(e, IncomeExpencesDAO.class.getName(), "getList()");
            Logger.getLogger(IncomeExpencesDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        DBConnection.releasConnection(connection);
        return incomeExpenceses;
    }

}
