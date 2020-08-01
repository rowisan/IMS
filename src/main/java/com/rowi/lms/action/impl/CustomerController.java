/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.action.impl;

import com.rowi.lms.action.Action;
import com.rowi.lms.common.FormAction;
import com.rowi.lms.common.SessionVariables;
import com.rowi.lms.common.SystemMessageType;
import com.rowi.lms.common.SystemMessage.CommonLogger;
import com.rowi.lms.modle.Customer;
import com.rowi.lms.services.CustomerSER;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

/**
 *
 * @author ROSHAN
 */
public class CustomerController extends Action {

    String msg = "";

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
        } else if (action.equals(FormAction.OTHER)) {
            try {
                String url;
                Customer customer = CustomerSER.search(request.getParameter("txtCustCodeH"), "0");
                if (customer != null) {
                    url = "/OtherChargeServlet?FUNC=" + FormAction.SAVE + "&txtCustCode=" + request.getParameter("txtCustCodeH") + "&txtName=" + request.getParameter("txtName");
                    msg = "";
                } else {
                    url = "/admin/customer.jsp?FUNC=" + FormAction.UPDATE;
                    msg = "Please select the valide customer.~" + SystemMessageType.WARNING;
                }
                doForwerd(url);
            } catch (SQLException ex) {
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (action.equals(FormAction.RECEIPT)) {
            try {
                String url;
                Customer customer = CustomerSER.search(request.getParameter("txtCustCodeH"), "0");
                if (customer != null) {
                    url = "/ReceiptAgPackageServlet?FUNC=" + FormAction.SAVE + "&txtCustCode=" + request.getParameter("txtCustCodeH") + "&txtName=" + request.getParameter("txtName");
                    msg = "";
                } else {
                    url = "/admin/customer.jsp?FUNC=" + FormAction.UPDATE;
                    msg = "Please select the valide customer.~" + SystemMessageType.WARNING;
                }
                doForwerd(url);
            } catch (SQLException ex) {
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (action.equals(FormAction.PACKAGE)) {
            try {
                String url;
                Customer customer = CustomerSER.search(request.getParameter("txtCustCodeH"), "0");
                if (customer != null) {
                    url = "/PackageServlet?FUNC=" + FormAction.SAVE + "&txtCustCode=" + request.getParameter("txtCustCodeH") + "&txtName=" + request.getParameter("txtName");
                    msg = "";
                } else {
                    url = "/admin/customer.jsp?FUNC=" + FormAction.UPDATE;
                    msg = "Please select the valide customer.~" + SystemMessageType.WARNING;
                }
                doForwerd(url);
            } catch (SQLException ex) {
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    protected void save() {
        Customer customer = new Customer();
        customer.setCode((String) request.getParameter("txtCustCode"));
        customer.setName((String) request.getParameter("txtName"));
        customer.setIdNo((String) request.getParameter("txtID"));
        customer.setDob((String) request.getParameter("from"));
        customer.setPhone((String) request.getParameter("txtPhone"));
        customer.setAddress1((String) request.getParameter("txtAddress1"));
        customer.setAddress2((String) request.getParameter("txtAddress2"));
        customer.setAddress3((String) request.getParameter("txtAddress3"));
        customer.setBranch((String) session.getAttribute(SessionVariables.BRANCH));

        msg = CustomerSER.save(customer);
        request.setAttribute(SessionVariables.MSG, msg);
        String[] c = msg.split("~");
        int i = Integer.parseInt(c[2]);
        RequestDispatcher dispatcher;
        if (i == 2) {
            dispatcher = request.getRequestDispatcher("/PackageServlet?FUNC=" + FormAction.SAVE);
        } else {
            dispatcher = request.getRequestDispatcher("/admin/customer.jsp?FUNC=" + FormAction.SAVE);
        }
        try {
            dispatcher.forward(request, response);
        } catch (ServletException ex) {
            CommonLogger.handle(ex, CustomerController.class.getName(), "save()");
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            CommonLogger.handle(ex, CustomerController.class.getName(), "save()");
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void search() {
        msg = "";
        boolean b = true;
        String code = request.getParameter("txtCustCode");
        String id = request.getParameter("txtID");
        if ((!code.equals("") && code != null) || (!id.equals("") && id != null)) {
            try {
                Customer customer = CustomerSER.search(code, id);

                if (customer == null) {
                    msg = "Customer not found.~" + SystemMessageType.WARNING;
                }
                request.setAttribute(SessionVariables.REQ_OBJECT, customer);
            } catch (SQLException ex) {
                msg = "Customer search Error.~" + SystemMessageType.ERROR;
                CommonLogger.handle(ex, CustomerController.class.getName(), "search()");
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (code.length() > 0) {
                msg = "Customer code or ID No. invalide.~" + SystemMessageType.WARNING;
            } else {
                b = !b;
                getList();
            }
        }
        if (!msg.equals("")) {
            request.setAttribute(SessionVariables.MSG, msg);
        }
        if (b) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/customer.jsp?FUNC=" + FormAction.SEARCH);
            request.setAttribute(SessionVariables.MSG, msg);
            try {
                dispatcher.forward(request, response);
            } catch (ServletException ex) {
                CommonLogger.handle(ex, CustomerController.class.getName(), "search()");
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                CommonLogger.handle(ex, CustomerController.class.getName(), "search()");
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void update() {

        String code = request.getParameter("txtCustCode");
        String indexNo = request.getParameter("txtCustCodeH");

        if (!code.equals("") && code != null) {
//            code = code.substring(3, 8);

            Customer customer = new Customer();
            customer.setIndexNo(indexNo);
            customer.setCode(code);
            customer.setName((String) request.getParameter("txtName"));
            customer.setIdNo((String) request.getParameter("txtID"));
            customer.setDob((String) request.getParameter("from"));
            customer.setPhone((String) request.getParameter("txtPhone"));
            customer.setAddress1((String) request.getParameter("txtAddress1"));
            customer.setAddress2((String) request.getParameter("txtAddress2"));
            customer.setAddress3((String) request.getParameter("txtAddress3"));
            customer.setBranch((String) request.getParameter("txtBranch"));

            msg = CustomerSER.updateCustomer(customer);
        } else {
            msg = "Customer selecting error.~" + SystemMessageType.WARNING;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/customer.jsp?FUNC=" + FormAction.UPDATE);
        request.setAttribute(SessionVariables.MSG, msg);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException ex) {
            CommonLogger.handle(ex, CustomerController.class.getName(), "update()");
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            CommonLogger.handle(ex, CustomerController.class.getName(), "update()");
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void delete() {

        String code = request.getParameter("txtCustCodeH");
        if (!code.equals("") && code != null && code.length() == 8) {
            code = code.substring(3, 8);
            msg = CustomerSER.delete(code);
        } else {
            msg = "Customer code invalide~" + SystemMessageType.WARNING;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/customer.jsp?FUNC=" + FormAction.UPDATE);
        request.setAttribute(SessionVariables.MSG, msg);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException ex) {
            CommonLogger.handle(ex, CustomerController.class.getName(), "delete()");
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            CommonLogger.handle(ex, CustomerController.class.getName(), "delete()");
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void getList() {
        ArrayList<Customer> customers;

        try {
            customers = CustomerSER.getList();
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/customer.jsp?FUNC=" + FormAction.GET_LIST);
            request.setAttribute(SessionVariables.REQ_OBJECT, customers);
            dispatcher.forward(request, response);
        } catch (SQLException ex) {
            CommonLogger.handle(ex, CustomerController.class.getName(), "getList()");
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            CommonLogger.handle(ex, CustomerController.class.getName(), "getList()");
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            CommonLogger.handle(ex, CustomerController.class.getName(), "getList()");
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void doForwerd(String url) {
        request.setAttribute(SessionVariables.MSG, msg);
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException ex) {
            CommonLogger.handle(ex, CustomerController.class.getName(), "save()");
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            CommonLogger.handle(ex, CustomerController.class.getName(), "save()");
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
