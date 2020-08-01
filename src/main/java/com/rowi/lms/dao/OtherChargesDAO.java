/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.dao;

import com.rowi.lms.common.status.ChargeStatus;
import com.rowi.lms.modle.Charge;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ROSHAN
 */
public class OtherChargesDAO {

    public static int save(Charge charge, Connection connection) throws SQLException {
        String sql = "INSERT INTO \n"
                + "				other_charge \n"
                + "				(charge, \n"
                + "				cpu, \n"
                + "				amount, \n"
                + "				remark, \n"
                + "				charge_rebit, \n"
                + "				status) \n"
                + "VALUES \n"
                + "				(\n"
                + "                                       '" + charge.getCode() + "',\n"
                + "                                       '" + charge.getCpu() + "',\n"
                + "                                       '" + charge.getAmount() + "',\n"
                + "                                       '" + charge.getRemark() + "',\n"
                + "                                       '" + charge.getChargeRebit() + "',\n"
                + "                                       '" + ChargeStatus.ACTIVE + "'\n"
                + "                                                                     )";
        PreparedStatement preparedStmt = connection.prepareStatement(sql, 1);
        preparedStmt.executeUpdate();
        ResultSet resTrans;
        resTrans = preparedStmt.getGeneratedKeys();
        if (resTrans.next()) {
            return resTrans.getInt(1);
        } else {
            return 0;
        }
    }

}
