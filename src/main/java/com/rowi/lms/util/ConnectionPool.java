package com.rowi.lms.util;

import com.rowi.lms.common.SystemMessage.CommonLogger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 *
 * Product: solSyn - DFCC MIS Solution <br/>
 * Description: Connection Pool for BillPay database connections <br/>
 * Copyright: Copyright, 2007 (c) <br/>
 * Company: M.I. Synergy (Pvt) Ltd <br/>
 *
 * @author Chamila Perera
 * @version 1.0
 */
public class ConnectionPool {

    private static ConnectionPool connectionPool;

    private int initialConnections = 5;
    //private int minConnections;
    private int maxConnJDBC = 10;
    private int totalConnections;
    private int usedCount = 0;
    private Vector connectionsJDBC = new Vector();

    private String user = "root";
    private String password = "123456";
    private String dbDriver = "com.mysql.jdbc.Driver";
    private String dbURL = "jdbc:mysql://";
    private String dbServer = "127.0.0.1:3306/";
    private String dbName = "lms_db";
    public static String logFilePath = "E:\\system";

    /**
     * Constructor
     */
    public ConnectionPool() {

        ResourceBundle rb = ResourceBundle.getBundle("System");
        dbURL = rb.getString("dbURL").trim();
        dbServer = rb.getString("dbServer").trim();
        dbDriver = rb.getString("dbDriver").trim();
        dbName = rb.getString("dbName").trim();
        user = rb.getString("user").trim();
        password = rb.getString("password").trim();
        logFilePath = rb.getString("logFilePath").trim();

        try {
            this.initialize();
        } catch (Exception e) {
            CommonLogger.handle(e, ConnectionPool.class.getName(), "processRequest()");
        }
    }

    /**
     * This method creates a new Connection Pool object <br/>
     * Creates only one instance of the object
     *
     * @return Connection Pool instance <tt>ConnectionPool</tt> object
     */
    public static synchronized ConnectionPool getInstance() {
        if (connectionPool == null || connectionPool.getJDBCConnection() == null) {
            connectionPool = new ConnectionPool();
        }

        return connectionPool;
    }

    /**
     * Destroy the object
     */
    public void finalize() {
        emptyPool();
    }

    /**
     * Empty the connection pool
     */
    public synchronized void emptyPool() {
        PooledConnectionJDBC tempPooledConnJDBC = null;

        try {
            for (int i = 0; i < connectionsJDBC.size(); i++) {
                tempPooledConnJDBC = (PooledConnectionJDBC) connectionsJDBC.elementAt(i);
                tempPooledConnJDBC.close();
                tempPooledConnJDBC = null;
            }
            connectionsJDBC.clear();
            connectionsJDBC = new Vector();
        } catch (Exception e) {
            CommonLogger.handle(e, ConnectionPool.class.getName(), "processRequest()");
        }
    }

    /*
     * Initialize the connection pool
     */
    public synchronized void initialize() {
        Connection conn = null;
        connectionsJDBC.clear();
        totalConnections = initialConnections;
        try {
            Class.forName(dbDriver);

            for (int x = 0; x < totalConnections; x++) {
                conn = DriverManager.getConnection(dbURL + dbServer + dbName, user, password);
                connectionsJDBC.addElement(new PooledConnectionJDBC(conn));
            }
        } catch (Exception e) {
            CommonLogger.handle(e, ConnectionPool.class.getName(), "processRequest()");
        }
    }

    /**
     * Return a Connection object from the JDBC ConnectionPool
     *
     * @return JDBC Connection as <tt>Connection</tt>
     * @throws Exception
     */
    public synchronized Connection getJDBCConnection() {
        Connection conn = null;
        Connection temp = null;
        PooledConnectionJDBC tempPooledConn1 = null;
        PooledConnectionJDBC tempPooledConn2 = null;

        try {
            if (totalConnections > usedCount) {
                usedCount++;
                for (int x = 0; x < totalConnections; x++) {
                    tempPooledConn1 = (PooledConnectionJDBC) connectionsJDBC.elementAt(x);
                    if (!tempPooledConn1.getInUse()) {
                        tempPooledConn1.setInUse(true);
                        conn = tempPooledConn1.getConnection();

                        if ((conn == null) || conn.isClosed()) {
                            conn = DriverManager.getConnection(dbURL + dbServer + dbName, user, password);
                        }
                        break;
                    }
                }
            } else if (maxConnJDBC > totalConnections) {
                totalConnections++;
                temp = DriverManager.getConnection(dbURL + dbServer + dbName, user, password);
                usedCount++;
                tempPooledConn2 = new PooledConnectionJDBC(temp);
                tempPooledConn2.setInUse(true);
                connectionsJDBC.addElement(tempPooledConn2);
                conn = tempPooledConn2.getConnection();
            } else {
            }
        } catch (Exception e) {
            CommonLogger.handle(e, ConnectionPool.class.getName(), "processRequest()");
        }
        return conn;
    }

    /**
     * Releases the connection back to the JDBC ConnectionPool
     *
     * @param conn
     */
    public synchronized void releaseJDBCConnection(Connection conn) {
        PooledConnectionJDBC tempPooledConn = null;

        try {
            conn.setAutoCommit(true);
            usedCount--;
            for (int x = 0; x < totalConnections; x++) {
                tempPooledConn = (PooledConnectionJDBC) connectionsJDBC.elementAt(x);
                if (tempPooledConn.getConnection().equals(conn)) {
                    tempPooledConn.setInUse(false);
                    /* 
                     * Removes additional connections
                     * Removes the connection from the pool
                     */
                    if (totalConnections > initialConnections) {
                        Object obj = connectionsJDBC.remove(x);
                        tempPooledConn.close();
                        tempPooledConn = null;
                        totalConnections--;
                        obj = null;
                    }

                    break;
                }
            }
        } catch (Exception e) {
            CommonLogger.handle(e, ConnectionPool.class.getName(), "processRequest()");
        }
    }

    /**
     * Returns a persistent JDBC Connection
     *
     * @return Persistent JDBC COnnection as <tt>Connection</tt>
     */
    public synchronized Connection getPersistenceConnection() {
        try {
            // DriverManager.registerDriver(new sun.jdbc.odbc.JdbcOdbcDriver());
            return DriverManager.getConnection(dbURL + dbServer + dbName, user, password);
        } catch (Exception e) {
            CommonLogger.handle(e, ConnectionPool.class.getName(), "processRequest()");
            return null;
        }
    }

    /**
     * Closes and destroy the JDBC Connection
     *
     * @param <tt>Connection</tt> JDBC Connection
     */
    public synchronized void releasePersistenceConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
            conn = null;
        } catch (Exception e) {
            CommonLogger.handle(e, ConnectionPool.class.getName(), "processRequest()");
        }
    }

    class PooledConnectionJDBC {

        private Connection conn = null;

        private boolean inuse = false;

        /**
         * Constructor
         *
         * @param <tt>conn</tt> JDBC COnnection
         */
        public PooledConnectionJDBC(Connection conn) {
            if (conn != null) {
                this.conn = conn;
            }
        }

        /**
         * Returns the JDBC Connection object
         *
         * @return JDBC Connection as <tt>Connection</tt>
         */
        public Connection getConnection() {
            return conn;
        }

        /**
         * Set the Connection in use status
         *
         * @param <tt>inuse</tt> Connection in use status
         */
        public void setInUse(boolean inuse) {
            this.inuse = inuse;
        }

        /**
         * Returns the Connection in use status
         *
         * @return Connection in use status as <tt>boolean</tt>
         */
        public boolean getInUse() {
            return inuse;
        }

        /**
         * Closes and destroy the JDBC COnnection
         */
        public void close() {
            try {
                if (conn != null) {
                    conn.close();
                }
                conn = null;
            } catch (SQLException e) {
                CommonLogger.handle(e, ConnectionPool.class.getName(), "processRequest()");
            }
        }
    };

}
