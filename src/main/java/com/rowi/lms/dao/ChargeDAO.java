/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.dao;

import com.rowi.lms.common.OtherChargeType;
import com.rowi.lms.common.status.ChargeStatus;
import com.rowi.lms.modle.Charge;
import com.rowi.lms.util.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author ROSHAN
 */
public class ChargeDAO {

    public static ArrayList<Charge> getList(Connection connection) throws SQLException {
        String sql = "SELECT * FROM charge WHERE status = '" + ChargeStatus.ACTIVE + "' AND charge_rebit = '" + OtherChargeType.CHARGE + "'";
        ArrayList<Charge> charges = new ArrayList<Charge>();

        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            Charge charge = new Charge();
            charge.setCode(resultSet.getString("code"));
            charge.setDescription(resultSet.getString("description"));
            charge.setDefaultAmount(resultSet.getDouble("default_amount"));
            charge.setPrintOrder(resultSet.getString("print_order"));
            charges.add(charge);
        }
        return charges;
    }

    public static Charge getCharge(String code, Connection connection) throws SQLException {
        String sql = "SELECT * FROM charge WHERE code = '" + code + "' AND status = '" + ChargeStatus.ACTIVE + "'";
        Charge charge = new Charge();

        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            charge.setCode(resultSet.getString("code"));
            charge.setDescription(resultSet.getString("description"));
            charge.setChargeType(resultSet.getString("charge_type"));
            charge.setDefaultAmount(resultSet.getDouble("default_amount"));
            charge.setMaxDiscount(resultSet.getDouble("max_discount"));
            charge.setMinExam(resultSet.getDouble("min_exam"));
            charge.setMinTrial(resultSet.getDouble("min_trial"));
            charge.setChargeRebit(resultSet.getString("charge_rebit"));
            charge.setPrintOrder(resultSet.getString("print_order"));
        }
        return charge;
    }

}
