/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.services;

import com.rowi.lms.util.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author ROSHAN
 */
public class LoginSER {

    public static boolean login(String userName, String password) throws SQLException {
        Connection connection = DBConnection.getConnection();
        return false;
    }
    
}
