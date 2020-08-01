/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.services;

import com.rowi.lms.dao.CustomerDAO;
import com.rowi.lms.modle.Customer;
import com.rowi.lms.modle.dto.CustomerHistoryDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ROSHAN
 */
public class CustomerSER {

    private static String msg;

    public static String save(Customer customer) {
        msg = CustomerDAO.save(customer);
        return msg;
    }

    public static String updateCustomer(Customer customer) {
        msg = CustomerDAO.update(customer);
        return msg;
    }

    public static Customer search(String code, String id) throws SQLException {
        Customer customer = CustomerDAO.search(code, id);
        return customer;
    }

    public static String delete(String code) {
        return CustomerDAO.delete(code);
    }

    public static ArrayList<Customer> getList() throws SQLException {
        return CustomerDAO.getList();
    }

    public static CustomerHistoryDTO getCustomerHistory(String code) {
        try {
            return CustomerDAO.getCustomerHistory(code);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerSER.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
