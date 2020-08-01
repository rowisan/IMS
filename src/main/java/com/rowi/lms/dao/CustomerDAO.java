/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.dao;

import com.rowi.lms.common.SystemMessageType;
import com.rowi.lms.common.SystemMessage.CommonLogger;
import com.rowi.lms.common.status.CustomerStatus;
import com.rowi.lms.modle.Customer;
import com.rowi.lms.modle.dto.CustomerHistoryDTO;
import com.rowi.lms.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ROSHAN
 */
public class CustomerDAO {

    private static String msg;

    public static String save(Customer customer) {
        Connection connection = DBConnection.getConnection();

        String s = "select * from customer where code = '" + customer.getCode() + "' and branch = '" + customer.getBranch() + "'";
        Statement statement;
        try {
            statement = (Statement) connection.createStatement();
            ResultSet resultSet = statement.executeQuery(s);

            if (!resultSet.next()) {
                String query = "INSERT INTO customer (code, name, id_no, dob, phone, address_1, address_2, address_3, branch, status) VALUES (?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement preparedStmt = null;

                try {
                    connection.setAutoCommit(false);
                    preparedStmt = connection.prepareStatement(query, 1);
                    preparedStmt.setString(1, customer.getCode());
                    preparedStmt.setString(2, customer.getName());
                    preparedStmt.setString(3, customer.getIdNo());
                    preparedStmt.setString(4, customer.getDob());
                    preparedStmt.setString(5, customer.getPhone());
                    preparedStmt.setString(6, customer.getAddress1());
                    preparedStmt.setString(7, customer.getAddress2());
                    preparedStmt.setString(8, customer.getAddress3());
                    preparedStmt.setString(9, customer.getBranch());
                    preparedStmt.setString(10, CustomerStatus.ACTIVE);
                    preparedStmt.execute();
                    connection.commit();
                    ResultSet r = preparedStmt.getGeneratedKeys();
                    int index = 0;
                    if (resultSet.next()) {
                        index = resultSet.getInt(1);
                    }
                    msg = "Customer saved Successfuly.~" + SystemMessageType.SUCCESS + "~" + index;
                } catch (SQLException ex) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex1) {
                        CommonLogger.handle(ex, CustomerDAO.class.getName(), "save()");
                        Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    msg = "Customer saving Error.~" + SystemMessageType.ERROR + "~" + 1;
                    Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        preparedStmt.close();
                    } catch (SQLException ex) {
                        CommonLogger.handle(ex, CustomerDAO.class.getName(), "save()");
                        msg = "Unable to close Prepared Statement.~" + SystemMessageType.WARNING + "~" + 1;
                        Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                msg = "Duplicate Customer code.~" + SystemMessageType.ERROR + "~" + 2;
            }
        } catch (SQLException ex) {
            msg = "Customer saving Error.~" + SystemMessageType.ERROR + "~" + 1;
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnection.releasConnection(connection);
        return msg;

    }

    public static String update(Customer customer) {
        Connection connection = DBConnection.getConnection();
        String query = "UPDATE customer SET name = ?, id_no = ?, dob = ?, phone = ?, address_1 = ?, address_2 = ?, address_3 = ?, branch = ? WHERE code = ? AND index_no = ?";
        PreparedStatement preparedStmt;
        try {
            connection.setAutoCommit(false);
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, customer.getName());
            preparedStmt.setString(2, customer.getIdNo());
            preparedStmt.setString(3, customer.getDob());
            preparedStmt.setString(4, customer.getPhone());
            preparedStmt.setString(5, customer.getAddress1());
            preparedStmt.setString(6, customer.getAddress2());
            preparedStmt.setString(7, customer.getAddress3());
            preparedStmt.setString(8, customer.getBranch());
            preparedStmt.setString(9, customer.getCode());
            preparedStmt.setString(10, customer.getIndexNo());
            preparedStmt.executeUpdate();
            connection.commit();
            preparedStmt.close();
            msg = "Customer update Successfuly.~" + SystemMessageType.SUCCESS;
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                CommonLogger.handle(ex1, CustomerDAO.class.getName(), "update()");
                Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            CommonLogger.handle(ex, CustomerDAO.class.getName(), "update()");
            msg = "Customer update error.~" + SystemMessageType.ERROR;
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnection.releasConnection(connection);
        return msg;
    }

    public static Customer search(String code, String id) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String wh = "";
        if ((code.equals("") || code == null)) {
            wh = "id_no = '" + id + "'";
        } else {
//            String branch = code.substring(0, 3);
//            code = code.substring(3, 8);
            wh = "code = '" + code + "'";
        }
        String sql = "SELECT * FROM customer WHERE " + wh;
        Customer customer = new Customer();

        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            customer.setIndexNo(resultSet.getString("index_no"));
            customer.setCode(resultSet.getString("code"));
            customer.setName(resultSet.getString("name"));
            customer.setIdNo(resultSet.getString("id_no"));
            customer.setDob(resultSet.getString("dob"));
            customer.setPhone(resultSet.getString("phone"));
            customer.setAddress1(resultSet.getString("address_1"));
            customer.setAddress2(resultSet.getString("address_2"));
            customer.setAddress3(resultSet.getString("address_3"));
            customer.setBranch(resultSet.getString("branch"));
        } else {
            customer = null;
        }
        DBConnection.releasConnection(connection);
        return customer;

    }

    public static String delete(String code) {
        Connection connection = DBConnection.getConnection();
        String query = "DELETE FROM customer WHERE code = ?";
        PreparedStatement preparedStmt;
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, code);
            preparedStmt.execute();
            preparedStmt.close();
            msg = "Customer delete Successfuly.~" + SystemMessageType.SUCCESS;
        } catch (SQLException ex) {
            CommonLogger.handle(ex, CustomerDAO.class.getName(), "delete()");
            msg = "Customer delete error.~" + SystemMessageType.ERROR;
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnection.releasConnection(connection);
        return msg;
    }

    public static ArrayList<Customer> getList() throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM customer WHERE status = '" + CustomerStatus.ACTIVE + "'";
        ArrayList<Customer> customers = new ArrayList<Customer>();

        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            Customer customer = new Customer();
            customer.setCode(resultSet.getString("code"));
            customer.setName(resultSet.getString("name"));
            customer.setIdNo(resultSet.getString("id_no"));
            customer.setDob(resultSet.getString("dob"));
            customer.setPhone(resultSet.getString("phone"));
            customer.setAddress1(resultSet.getString("address_1"));
            customer.setAddress2(resultSet.getString("address_2"));
            customer.setAddress3(resultSet.getString("address_3"));
            customer.setBranch(resultSet.getString("branch"));
            customers.add(customer);
        }
        DBConnection.releasConnection(connection);
        return customers;
    }

    public static CustomerHistoryDTO getCustomerHistory(String code) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "select \n"
                + "				customer.code, \n"
                + "				customer.name, \n"
                + "				customer.id_no,\n"
                + "				customer.phone, \n"
                + "				customer.exam_status, \n"
                + "				customer.trial_status,\n"
                + "				package.code as package, \n"
                + "				package.description, \n"
                + "				customer_package_unit.amount,\n"
                + "				customer_package_unit.paid, \n"
                + "				customer_package_unit.min_exam, \n"
                + "				customer_package_unit.min_trial \n"
                + "from \n"
                + "				customer\n"
                + "				left join customer_package_unit on customer_package_unit.customer = customer.index_no\n"
                + "				left join customer_package on customer_package_unit.index_no = customer_package.customer_package_unit\n"
                + "				left join package on package.code = customer_package.package\n"
                + "where \n"
                + "				customer.code = '" + code + "' and\n"
                + "				customer_package_unit.`status` = 'ACTIVE'";

        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        CustomerHistoryDTO customerHistoryDTO = new CustomerHistoryDTO();
        while (resultSet.next()) {
            customerHistoryDTO.setCode(resultSet.getString("code"));
            customerHistoryDTO.setName(resultSet.getString("name"));
            customerHistoryDTO.setNic(resultSet.getString("id_no"));
            customerHistoryDTO.setPhone(resultSet.getString("phone"));
            customerHistoryDTO.setPackkage(resultSet.getString("package"));
            customerHistoryDTO.setDescription(resultSet.getString("description"));
            customerHistoryDTO.setTotalAmount(resultSet.getDouble("amount"));
            customerHistoryDTO.setPaidAmount(resultSet.getDouble("paid"));
            customerHistoryDTO.setMinOfExam(resultSet.getDouble("paid"));
            customerHistoryDTO.setMinOfTrial(resultSet.getDouble("paid"));
            customerHistoryDTO.setExamStatus(resultSet.getString("exam_status"));
            customerHistoryDTO.setTrialStatus(resultSet.getString("trial_status"));
        }
        DBConnection.releasConnection(connection);
        return customerHistoryDTO;
    }

}
