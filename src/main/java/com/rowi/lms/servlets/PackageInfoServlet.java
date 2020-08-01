/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.servlets;

import com.rowi.lms.common.SystemMessage.CommonLogger;
import com.rowi.lms.modle.VehiclePackage;
import com.rowi.lms.services.PackageSER;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ROSHAN
 */
@WebServlet(name = "PackageInfoServlet", urlPatterns = {"/PackageInfoServlet"})
public class PackageInfoServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String package1 = request.getParameter("package");
        try {
            VehiclePackage vehiclePackage = PackageSER.getPackageInfo(package1);
            /* TODO output your page here. You may use following sample code. */
            out.println("<body>");
            out.println("<table ><tr><td>Package Code </td><td>" + vehiclePackage.getCode() + "</td></tr>");
            out.println("<tr><td>Package Description </td><td>" + vehiclePackage.getDescription() + "</td></tr>");
            out.println("<tr><td>Package Amount </td><td>" + vehiclePackage.getAmount() + "</td></tr>");
            out.println("<tr><td>Package Min Amount for Exam </td><td>" + vehiclePackage.getMinForExam() + "</td></tr>");
            out.println("<tr><td>Package Min Amount for Trial </td><td>" + vehiclePackage.getMinForTrial() + "</td></tr><table>");
            out.println("</body>");
        } catch (SQLException ex) {
            CommonLogger.handle(ex, PackageInfoServlet.class.getName(), "processRequest()");
            Logger.getLogger(PackageInfoServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
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
