/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.common.SystemMessage;

import com.rowi.lms.util.ConnectionPool;
import com.rowi.lms.util.Utilitys;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ROSHAN
 */
public class CommonLogger {

    private static String logFile = "/opt/PROJECT/LMS/logs";
    private static String currentDateTime = null;

    /**
     *
     * @param e Exception
     * @param c Class Name
     * @param msg Message to print
     * <br/>
     * @see
     * <b>yyyy-MM-dd hh:mm:ss ERROR : com.class.Name ---> Message --->
     * Exception<b>
     */
    public static void handle(Exception e, String c, String msg) {
        try {
            System.out.println("Exception In : " + c + " ---> " + msg);
            writeToFile("ERROR : " + c + " ---> " + msg + " ---> " + e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            System.out.println("ERROR: SystemMessageBillPay.writeToFile() - " + ex.getMessage());
            Logger.getLogger(CommonLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param type Log Type (ERROR, DEBUG, INFOR, DATA)
     * @param c Class Name (com.class.Name)
     * @param msg Message to print
     * <br/>
     * @see
     * <b>yyyy-MM-dd hh:mm:ss Type : com.class.Name ---> Message<b>
     */
    public static void log(String type, String c, String msg) {
        try {
            writeToFile(type + " : " + c + " ---> " + msg);
        } catch (Exception ex) {
            System.out.println("ERROR: SystemMessageBillPay.writeToFile() - " + ex.getMessage());
            Logger.getLogger(CommonLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static synchronized void writeToFile(String message) throws Exception {
        BufferedWriter buf = null;
        logFile = ConnectionPool.logFilePath;
        String dateTime = Utilitys.getCurrentDate() + " " + Utilitys.getCurrentTime();
        String log = logFile + " " + Utilitys.systemDate() + ".log";
        try {
            buf = new BufferedWriter(new FileWriter(log, true));
            buf.newLine();
            buf.write(dateTime + " : " + message);
            buf.close();
        } catch (Exception e) {
            System.out.println(dateTime + " : " + "ERROR : com.rowi.lms.common.SystemMessage.CommonLogger.writeToFile() - " + e.getMessage());
        }
    }
}
