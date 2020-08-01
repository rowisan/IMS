/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.services;

import com.rowi.lms.dao.SettlementDAO;
import com.rowi.lms.modle.Settlement;
import com.rowi.lms.util.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ROSHAN
 */
public class SettlementSER {

    public static ArrayList<Settlement> getSettlements(String cpu) throws SQLException {
        Connection connection = DBConnection.getConnection();
        ArrayList<Settlement> settlements = SettlementDAO.getSettlements(cpu, connection);
        return settlements;
    }

}
