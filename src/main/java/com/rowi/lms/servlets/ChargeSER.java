/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.servlets;

import com.rowi.lms.dao.ChargeDAO;
import com.rowi.lms.modle.Charge;
import com.rowi.lms.util.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ROSHAN
 */
public class ChargeSER {
    public static ArrayList<Charge> getList() throws SQLException {
        Connection connection = DBConnection.getConnection();
        ArrayList<Charge> charges = ChargeDAO.getList(connection);
        DBConnection.releasConnection(connection);
        return charges;
    }

    public static Charge getCharge(String code) throws SQLException {
        Connection connection = DBConnection.getConnection();
        Charge charge = ChargeDAO.getCharge(code, connection);
        DBConnection.releasConnection(connection);
        return charge;
    }

}
