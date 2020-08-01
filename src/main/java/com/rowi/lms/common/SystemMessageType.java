/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.common;

/**
 *
 * @author ROSHAN
 */
public class SystemMessageType {

    public static final String SUCCESS = "SUCCESS";
    public static final String WARNING = "WARNING";
    public static final String INFO = "INFO";
    public static final String ERROR = "ERROR";

    public static String getMsgStyle(String msgType) {
        String style = "success-msg";
        if (msgType.equals(SUCCESS)) {
            style = "success-msg";
        } else if (msgType.equals(INFO)) {
            style = "info-msg";
        } else if (msgType.equals(WARNING)) {
            style = "warning-msg";
        } else if (msgType.equals(ERROR)) {
            style = "error-msg";
        }
        return style;
    }
}
