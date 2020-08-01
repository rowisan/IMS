/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ROSHAN
 */
public class DBConnection {

    private static String url = "jdbc:mysql://localhost:3306/lms_db";
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String usernsme = "root";
    private static String pasword = "123456";
    private static Connection con;
    private static ConnectionPool connectionPool;

    public static void ConnectionPoolInit() {
        connectionPool = ConnectionPool.getInstance();
    }

    public static void releasConnection(Connection con) {
        connectionPool.releaseJDBCConnection(con);
    }

    public static Connection getConnection() {
        if (connectionPool == null) {
            ConnectionPoolInit();
        }
        con = connectionPool.getJDBCConnection();
        if (con == null) {
            connectionPool.emptyPool();
            ConnectionPoolInit();
            con = connectionPool.getJDBCConnection();
        }
        return con;
    }
}
