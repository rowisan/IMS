/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.controller;

import com.rowi.lms.action.Action;
import com.rowi.lms.common.SystemMessage.CommonLogger;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ROSHAN
 */
@WebServlet(name = "ActionController", urlPatterns = {"/ActionController/*"})
public class ActionController extends HttpServlet {

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
        HttpSession session = request.getSession();
        String path = request.getPathInfo();
        path = path.substring(1);
        String baseActionPath = "com.rowi.lms.action.impl.";
        String actionClassPath = baseActionPath + path;
        Action action;
        try {
            Class actionClass = Class.forName(actionClassPath);
            action = (Action) actionClass.newInstance();
            action.setRequest(request);
            action.setResponse(response);
            action.setSession(session);
            action.run();
        } catch (ClassNotFoundException ex) {
            CommonLogger.handle(ex, ActionController.class.getName(), "processRequest()");
            Logger.getLogger(ActionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            CommonLogger.handle(ex, ActionController.class.getName(), "processRequest()");
            Logger.getLogger(ActionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            CommonLogger.handle(ex, ActionController.class.getName(), "processRequest()");
            Logger.getLogger(ActionController.class.getName()).log(Level.SEVERE, null, ex);
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
