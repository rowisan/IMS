/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.action.impl;

import com.rowi.lms.action.Action;
import com.rowi.lms.bindinglayer.OtherChargeMVB;
import com.rowi.lms.common.FormAction;
import com.rowi.lms.common.SessionVariables;
import com.rowi.lms.common.SystemMessage.CommonLogger;
import com.rowi.lms.modle.vdo.OtherChargeVDO;
import com.rowi.lms.util.Utilitys;
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
public class OtherChargeController extends Action {

    private String msg;

    @Override
    public void run() {
        String action = (String) request.getParameter("txtAction");
        if (action.equals(FormAction.SAVE)) {
            save();
        } else if (action.equals(FormAction.UPDATE)) {
            update();
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
        try {
//            String packageCode = request.getParameter("txtPackage");
//            String custCode = request.getParameter("txtCustCode");
//            custCode = custCode.substring(3, 8);
//            boolean extPackage = Boolean.parseBoolean(request.getParameter("extPackage"));
            String code = request.getParameter("txtCharge");
            double amount = Double.valueOf(request.getParameter("txtAmount"));
            String remark = request.getParameter("txtRemark");
//            String customer = request.getParameter("txtCustCode");
            String customer = request.getParameter("txtCustCodeH");
            String cpu = request.getParameter("txtPackage");
            String minExam = request.getParameter("txtMinExam");
            String minTrial = request.getParameter("txtMinTrial");
            String user = (String) session.getAttribute(SessionVariables.USER);
            String branch = (String) session.getAttribute(SessionVariables.BRANCH);

            OtherChargeVDO otherChargeVDO = new OtherChargeVDO();
            otherChargeVDO.setCustomer(customer);
            otherChargeVDO.setCpu(cpu);
            otherChargeVDO.setCharge(code);
            otherChargeVDO.setRemark(remark);
            otherChargeVDO.setAmount(String.valueOf(amount));
            otherChargeVDO.setMinExam(minExam);
            otherChargeVDO.setMinTrial(minTrial);
            otherChargeVDO.setUser(user);
            otherChargeVDO.setBranch(branch);

            msg = OtherChargeMVB.save(otherChargeVDO);
            request.setAttribute(SessionVariables.MSG, msg);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/OtherChargeServlet?FUNC=" + FormAction.SAVE);
            dispatcher.forward(request, response);
        } catch (ServletException ex) {
            CommonLogger.handle(ex, OtherChargeController.class.getName(), "save()");
            Logger.getLogger(OtherChargeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            CommonLogger.handle(ex, OtherChargeController.class.getName(), "save()");
            Logger.getLogger(OtherChargeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            CommonLogger.handle(ex, OtherChargeController.class.getName(), "save()");
            Logger.getLogger(OtherChargeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void search() {
        try {
            String packageCode = request.getParameter("txtPackage");
            String indexNo = request.getParameter("txtCustCodeH");
//            custCode = custCode.substring(3, 8);
            boolean extPackage = Boolean.parseBoolean(request.getParameter("extPackage"));
            RequestDispatcher dispatcher = request.getRequestDispatcher("/OtherChargeServlet?FUNC=" + FormAction.SEARCH);
            dispatcher.forward(request, response);
        } catch (ServletException ex) {
            CommonLogger.handle(ex, OtherChargeController.class.getName(), "search()");
            Logger.getLogger(OtherChargeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            CommonLogger.handle(ex, OtherChargeController.class.getName(), "search()");
            Logger.getLogger(OtherChargeController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

}
