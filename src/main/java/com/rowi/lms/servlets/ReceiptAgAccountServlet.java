/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.servlets;

import com.rowi.lms.common.FormAction;
import com.rowi.lms.common.SessionVariables;
import com.rowi.lms.common.SystemMessage.CommonLogger;
import com.rowi.lms.common.TransactionType;
import com.rowi.lms.modle.Account;
import com.rowi.lms.modle.Narration;
import com.rowi.lms.services.AccountSER;
import com.rowi.lms.services.NarrationSER;
import java.io.IOException;
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
@WebServlet(name = "ReceiptAgAccountServlet", urlPatterns = {"/ReceiptAgAccountServlet"})
public class ReceiptAgAccountServlet extends HttpServlet {

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
            msg = (String) request.getAttribute(SessionVariables.MSG);
            ArrayList<Narration> narrations;
            narrations = NarrationSER.getList(TransactionType.RECEIPT);
            ArrayList<Account> accounts = AccountSER.getAccounts("'A', 'I'");
            
            request.setAttribute(SessionVariables.MSG, msg);
            request.setAttribute("account", accounts);
            request.setAttribute("narration", narrations);
            RequestDispatcher dispatcher = request.getRequestDispatcher("./admin/recipt_vou_ag_account.jsp?FUNC=" + FormAction.SAVE);
            dispatcher.include(request, response);
//            response.sendRedirect("${pageContext.request.contextPath}/admin/add_package.jsp?FUNC=" + FormAction.SAVE);
        } catch (IOException ex) {
            CommonLogger.handle(ex, ReceiptAgAccountServlet.class.getName(), "processRequest()");
            Logger.getLogger(ReceiptAgAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
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
