/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.action.impl;

import com.rowi.lms.action.Action;
import com.rowi.lms.common.FormAction;
import com.rowi.lms.common.SessionVariables;
import com.rowi.lms.services.LoginSER;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

/**
 *
 * @author ROSHAN
 */
public class LoginController extends Action {

    private String msg;

    @Override
    public void run() {
        String action = (String) request.getParameter("txtAction");
        System.out.println("Action : " + action);
        if (action.equals(FormAction.LOGIN)) {
            doLogin();
        } else if (action.equals(FormAction.LOGOUT)) {
            doLogout();
        } else if (action.equals(FormAction.SEARCH)) {
            search();
        } else if (action.equals(FormAction.DELETE)) {
            delete();
        } else if (action.equals(FormAction.HISTORY)) {
            history();
        }
    }

    @Override
    protected void save() {
    }

    @Override
    protected void search() {
    }

    @Override
    protected void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void getList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void history() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void doLogin() {
        String userName = (String) request.getParameter("txtName");
        String password = (String) request.getParameter("txtPassword");
        boolean b = false;

        try {
            b = LoginSER.login(userName, password);
            request.setAttribute(SessionVariables.MSG, msg);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/PackageServlet?FUNC=" + FormAction.SAVE);
            dispatcher.forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void doLogout() {
        String userName = (String) request.getParameter("txtName");
        String password = (String) request.getParameter("txtPassword");
        boolean b = false;

        try {
            b = LoginSER.login(userName, password);
            request.setAttribute(SessionVariables.MSG, msg);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/LoginServlet?FUNC=" + FormAction.SAVE);
            dispatcher.forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
