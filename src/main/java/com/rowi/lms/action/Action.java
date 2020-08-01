/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import org.apache.catalina.core.ApplicationContext;

/**
 *
 * @author ROSHAN
 */
public abstract class Action {
    protected transient HttpServletRequest request;
    protected transient HttpServletResponse response;
    protected transient HttpSession session;
//    protected transient ApplicationContext context;
    
    public abstract void run();

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

//    public ApplicationContext getContext() {
//        return context;
//    }
//
//    public void setContext(ApplicationContext context) {
//        this.context = context;
//    }
    
    protected abstract void save();
    protected abstract void search();
    protected abstract void update();
    protected abstract void delete();
    protected abstract void getList();
}
