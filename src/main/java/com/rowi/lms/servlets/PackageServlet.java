/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.servlets;

import com.rowi.lms.common.FormAction;
import com.rowi.lms.common.SessionVariables;
import com.rowi.lms.common.SystemMessageType;
import com.rowi.lms.common.SystemMessage.CommonLogger;
import com.rowi.lms.modle.Customer;
import com.rowi.lms.modle.CustomerPackage;
import com.rowi.lms.modle.VehiclePackage;
import com.rowi.lms.services.PackageSER;
import com.rowi.lms.util.Utilitys;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ROSHAN
 */
@WebServlet(name = "PackageServlet", urlPatterns = {"/PackageServlet"})
public class PackageServlet extends HttpServlet {

    private static String msg;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String FUNC = request.getParameter(SessionVariables.FUNC);
            ArrayList<VehiclePackage> packages;
            packages = PackageSER.getList();
            String custCode = request.getParameter("txtCustCode");
            String indexNo = request.getParameter("txtCustCodeH");
            CustomerPackage customerPackage = null;
            Customer customer = null;
            //Get custCode using msg
            String msg1 = (String) request.getAttribute(SessionVariables.MSG);
//            if (msg1 != null && !msg1.equals("") && custCode.equals("")) {
//                String[] msga = msg1.split("~");
//                msg = msg1;
//                if (msga.length == 3) {
//                    String branch = request.getParameter("txtBranch");
//                    custCode = msga[2];
//                    int len = custCode.length();
//                    if (len < 5) {
//                        for (int i = 0; i < 5 - len; i++) {
//                            System.out.println("ccc " + custCode);
//                            custCode = "0" + custCode;
//                        }
//                    }
//                    custCode = branch + custCode;
//                }
//            }
            if (custCode != null && !custCode.equals("")) {
//                if (custCode.length() == 8) {
                customer = PackageSER.getCustomer(custCode);
                msg = null;
                if (customer == null) {
                    msg = "Customer not found.~" + SystemMessageType.WARNING;
                } else {
                    customerPackage = PackageSER.getCustomerPackage(customer.getIndexNo());
                }
//                } else {
//                    String branch = request.getParameter("txtBranch");
//                    String cust = branch + Utilitys.setIDFormat(custCode);
//                    customer = PackageSER.getCustomer(cust);
//                    if (customer == null) {
//                        msg = "Invalide customer code.~" + SystemMessageType.ERROR;
//                    } else {
//                        customerPackage = PackageSER.getCustomerPackage(cust.substring(3, 8));
//                    }
//                }
            } else if (!FUNC.equals(FormAction.INIT)) {
                msg = "Pleas select the customer.~" + SystemMessageType.INFO;
            }
            request.setAttribute(SessionVariables.MSG, msg);
            request.setAttribute("customer", customer);
            request.setAttribute("extPackage", customerPackage);
            request.setAttribute("packages", packages);
            RequestDispatcher dispatcher = request.getRequestDispatcher("./admin/add_package.jsp?FUNC=" + FUNC);
            dispatcher.include(request, response);
//            response.sendRedirect("${pageContext.request.contextPath}/admin/add_package.jsp?FUNC=" + FormAction.SAVE);
        } catch (IOException ex) {
            CommonLogger.handle(ex, PackageServlet.class.getName(), "processRequest()");
            Logger.getLogger(PackageServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            CommonLogger.handle(ex, PackageServlet.class.getName(), "processRequest()");
            Logger.getLogger(PackageServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
