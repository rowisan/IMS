/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 *
 * @author ROSHAN
 */


public class Utilitys {

    private static String javaDate(String fmt) {
        String dd;
        TimeZone gmt530 = TimeZone.getTimeZone("GMT");
        gmt530.setRawOffset((11 * 30) * 60 * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat(fmt);
        formatter.setTimeZone(gmt530);
        dd = formatter.format(new java.util.Date());
        return dd;
    }

    public static String getCurrentDate(String format) {
        return javaDate(format);
    }

    public static String getCurrentDate() {
        return javaDate("yyyy-MM-dd");
    }

    public static String getCurrentTime() {
        return javaDate("hh:mm:ss");
    }

    public static String systemDate() {
        return javaDate("yyyy-MM-dd");
    }

    public static String SystemTime() {
        return javaDate("hh:mm:ss");
    }

//    public static String getCustomerCode(String customer) {
//        return customer.substring(3, 8);
//    }
    public static String setIDFormat(String id) {
        String code = id;
        for (int i = id.length(); i < 5; i++) {
            code = "0" + code;
        }
        return code;
    }

    public static String getCurrencyValue(String amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        return decimalFormat.format(Double.parseDouble(amount));
    }
    
    public static String getDecimalValue(String amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(Double.parseDouble(amount));
    }
}
