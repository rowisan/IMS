/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.action.impl;

import com.rowi.lms.action.Action;
import com.rowi.lms.common.FormAction;
import com.rowi.lms.common.SessionVariables;
import com.rowi.lms.common.SystemMessage.CommonLogger;
import com.rowi.lms.services.PackageSER;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

/**
 *
 * @author ROSHAN
 */
public class PackageController extends Action {

    private static String msg;
    String custCode = "";
    String custName = "";

    @Override
    public void run() {
        String action = (String) request.getParameter("txtAction");
        System.out.println("Action : " + action);
        if (action.equals(FormAction.SAVE)) {
            save();
        } else if (action.equals(FormAction.UPDATE)) {
            update();
        } else if (action.equals(FormAction.SEARCH)) {
            search();
        } else if (action.equals(FormAction.DELETE)) {
            delete();
        } else if (action.equals(FormAction.INIT)) {
            init();
        } else if (action.equals(FormAction.HISTORY)) {
            history();
        }
    }

    @Override
    protected void save() {
        try {
            String packageCode = request.getParameter("txtPackage");
            String custom = request.getParameter("txtCustCodeH");
//            custom = custom.substring(3, 8);
            boolean extPackage = Boolean.parseBoolean(request.getParameter("extPackage"));
            msg = PackageSER.save(packageCode, custom);
            request.setAttribute(SessionVariables.MSG, msg);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/OtherChargeServlet");
            dispatcher.forward(request, response);
        } catch (ServletException ex) {
            CommonLogger.handle(ex, PackageController.class.getName(), "save()");
            Logger.getLogger(PackageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            CommonLogger.handle(ex, PackageController.class.getName(), "save()");
            Logger.getLogger(PackageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void update() {
        try {
            String packageCode = request.getParameter("txtPackage");
            String custCode = request.getParameter("txtCustCode");
            String cpu = request.getParameter("txtCPU");
            String extPackage = request.getParameter("txtExtPackage");
            String ExtPackageId = request.getParameter("txtExtPackageId");
            custCode = custCode.substring(3, 8);
            boolean isExtPackage = Boolean.parseBoolean(request.getParameter("extPackage"));
            msg = PackageSER.update(packageCode, custCode, cpu, extPackage, ExtPackageId);
            request.setAttribute(SessionVariables.MSG, msg);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/add_package.jsp?FUNC=" + FormAction.UPDATE);
            dispatcher.forward(request, response);
        } catch (ServletException ex) {
            CommonLogger.handle(ex, PackageController.class.getName(), "update()");
            Logger.getLogger(PackageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            CommonLogger.handle(ex, PackageController.class.getName(), "update()");
            Logger.getLogger(PackageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void search() {
        init();
//        try {
//            dispatcher.forward(request, response);
//        } catch (ServletException ex) {
//            Logger.getLogger(PackageController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(PackageController.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
        String FUNC = request.getParameter(SessionVariables.FUNC);
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/PackageHistoryServlet?FUNC=" + FUNC);
            dispatcher.forward(request, response);
        } catch (ServletException ex) {
            CommonLogger.handle(ex, PackageController.class.getName(), "history()");
            Logger.getLogger(PackageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            CommonLogger.handle(ex, PackageController.class.getName(), "history()");
            Logger.getLogger(PackageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init() {
        String FUNC = request.getParameter(SessionVariables.FUNC);
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/PackageServlet?FUNC=" + FUNC);
            dispatcher.forward(request, response);
        } catch (ServletException ex) {
            CommonLogger.handle(ex, PackageController.class.getName(), "init()");
            Logger.getLogger(PackageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            CommonLogger.handle(ex, PackageController.class.getName(), "init()");
            Logger.getLogger(PackageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
