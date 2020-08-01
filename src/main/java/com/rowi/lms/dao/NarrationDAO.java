/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.dao;

import com.rowi.lms.common.TransactionType;
import com.rowi.lms.common.status.CustomerStatus;
import com.rowi.lms.common.status.NarrationStatus;
import com.rowi.lms.modle.Customer;
import com.rowi.lms.modle.Narration;
import com.rowi.lms.services.NarrationSER;
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
public class NarrationDAO {

    public static ArrayList<Narration> getList(String type) {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM narration WHERE status = '" + NarrationStatus.ACTIVE + "' AND type IN ('"+type+"', 'X')";
        ArrayList<Narration> narrations = new ArrayList<Narration>();

        Statement statement;
        try {
            statement = (Statement) connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Narration narration = new Narration();
                narration.setCode(resultSet.getString("code"));
                narration.setDescription(resultSet.getString("description"));
                narrations.add(narration);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NarrationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnection.releasConnection(connection);
        return narrations;
    }

}
