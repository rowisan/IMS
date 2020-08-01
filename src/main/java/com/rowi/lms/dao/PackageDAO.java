/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.dao;

import com.rowi.lms.common.OtherChargeType;
import com.rowi.lms.common.status.CustomerPackageStatus;
import com.rowi.lms.common.status.CustomerPackageUnitStatus;
import com.rowi.lms.common.status.PackageStatus;
import com.rowi.lms.modle.Charge;
import com.rowi.lms.modle.Customer;
import com.rowi.lms.modle.CustomerPackage;
import com.rowi.lms.modle.VehiclePackage;
import com.rowi.lms.modle.vdo.VoucherVDO;
import com.rowi.lms.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author ROSHAN
 */
public class PackageDAO {

    private static String msg;

    public static CustomerPackage getCustomerPackage(String code) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT \n"
                + "				customer_package.index_no indexNo, package.code PackageCode, package.description, customer_package.customer_package_unit cpu\n"
                + "FROM \n"
                + "				customer_package \n"
                + "	LEFT JOIN \n"
                + "					package \n"
                + "	ON \n"
                + "					package.code = customer_package.package\n"
                + "WHERE \n"
                + "				customer_package.customer = '" + code + "' AND customer_package.status = '" + CustomerPackageStatus.ACTIVE + "'";
        CustomerPackage customerPackage = new CustomerPackage();
        VehiclePackage vehiclePackage = new VehiclePackage();
        Customer customer = new Customer();

        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            vehiclePackage.setCode(resultSet.getString("PackageCode"));
            vehiclePackage.setDescription(resultSet.getString("description"));
            customerPackage.setVehiclePackage(vehiclePackage);
            customerPackage.setCpu(resultSet.getInt("cpu"));
            customerPackage.setIndexNo(resultSet.getInt("indexNo"));
        } else {
            customerPackage = null;
        }
        DBConnection.releasConnection(connection);
        return customerPackage;
    }

    public static ArrayList<CustomerPackage> getCustomerPackages(String code) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT \n"
                + "				customer_package.index_no indexNo, package.code PackageCode, package.description, customer_package.customer_package_unit cpu\n"
                + "FROM \n"
                + "				customer_package \n"
                + "	LEFT JOIN \n"
                + "					package \n"
                + "	ON \n"
                + "					package.code = customer_package.package\n"
                + "WHERE \n"
                + "				customer_package.customer = '" + code + "' AND customer_package.status = '" + CustomerPackageStatus.ACTIVE + "'";
        ArrayList<CustomerPackage> customerPackages = new ArrayList<CustomerPackage>();
        VehiclePackage vehiclePackage = new VehiclePackage();
        Customer customer = new Customer();

        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            CustomerPackage customerPackage = new CustomerPackage();
            vehiclePackage.setCode(resultSet.getString("PackageCode"));
            vehiclePackage.setDescription(resultSet.getString("description"));
            customerPackage.setVehiclePackage(vehiclePackage);
            customerPackage.setCpu(resultSet.getInt("cpu"));
            customerPackage.setIndexNo(resultSet.getInt("indexNo"));
            customerPackages.add(customerPackage);
        }
        DBConnection.releasConnection(connection);
        return customerPackages;
    }

    public static VehiclePackage getPackage(String code, Connection connection) throws SQLException {
        String sql = "SELECT \n"
                + "				* \n"
                + "FROM \n"
                + "				package \n"
                + "WHERE \n"
                + "				package.code = '" + code + "'";
        VehiclePackage vehiclePackage = new VehiclePackage();

        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            vehiclePackage.setCode(resultSet.getString("code"));
            vehiclePackage.setDescription(resultSet.getString("description"));
            vehiclePackage.setAmount(resultSet.getDouble("fee"));
            vehiclePackage.setMinForExam(resultSet.getDouble("min_for_exam"));
            vehiclePackage.setMinForTrial(resultSet.getDouble("min_for_trial"));
//            vehiclePackage.setAmount(resultSet.getDouble("discount"));
        } else {
            vehiclePackage = null;
        }
        return vehiclePackage;
    }

    public static ArrayList<VehiclePackage> getList() throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM package WHERE status = '" + PackageStatus.ACTIVE + "'";
        ArrayList<VehiclePackage> packages = new ArrayList<VehiclePackage>();

        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            VehiclePackage vehiclePackage = new VehiclePackage();
            vehiclePackage.setCode(resultSet.getString("code"));
            vehiclePackage.setDescription(resultSet.getString("description"));
            packages.add(vehiclePackage);
        }
        DBConnection.releasConnection(connection);
        return packages;
    }
    
    public static String getPackage(String cpu) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM customer_package WHERE customer_package_unit = '" + cpu + "' AND status = '" + PackageStatus.ACTIVE + "'";
        String regPackage = ""; 
        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            regPackage = resultSet.getString("package");
        }
        DBConnection.releasConnection(connection);
        return regPackage;
    }

    public static Customer getCustomer(String code) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM customer WHERE code = '" + code + "'";
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

    public static VehiclePackage getPackageInfo(String code) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM package WHERE code = '" + code + "'";
        VehiclePackage vehiclePackage = new VehiclePackage();

        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            vehiclePackage.setCode(resultSet.getString("code"));
            vehiclePackage.setDescription(resultSet.getString("description"));
            vehiclePackage.setAmount(resultSet.getDouble("fee"));
            vehiclePackage.setMinForExam(resultSet.getDouble("min_for_exam"));
            vehiclePackage.setMinForTrial(resultSet.getDouble("min_for_trial"));
        } else {
            vehiclePackage = null;
        }
        DBConnection.releasConnection(connection);
        return vehiclePackage;
    }

    public static int save(VehiclePackage vp, String custCode, Connection connection) throws SQLException {
        int genNo = insertCPU(vp, custCode, connection);
        return genNo;
    }

    private static int insertCPU(VehiclePackage vp, String custCode, Connection connection) throws SQLException {
        //INSERT CPU
        String sql = "INSERT INTO \n"
                + "				customer_package_unit \n"
                + "				(customer, \n"
                + "				min_exam, \n"
                + "				min_trial, \n"
                + "				amount, \n"
                + "				total_payble, \n"
                + "				balance, \n"
                + "				status) \n"
                + "VALUES \n"
                + "				('" + custCode + "',\n"
                + "                                       '" + vp.getMinForExam() + "',\n"
                + "                                       '" + vp.getMinForTrial() + "',\n"
                + "                                       '" + vp.getAmount() + "',\n"
                + "                                       '" + vp.getAmount() + "',\n"
                + "                                       '" + vp.getAmount() + "',\n"
                + "                                       '" + CustomerPackageUnitStatus.ACTIVE + "'\n"
                + "				)";
        PreparedStatement preparedStmt = connection.prepareStatement(sql, 1);
        preparedStmt.executeUpdate();
        ResultSet resTrans;
        resTrans = preparedStmt.getGeneratedKeys();
        if (resTrans.next()) {
            return resTrans.getInt(1);
        } else {
            return 0;
        }
    }

    public static int insertCustomerPackage(VehiclePackage vp, String custCode, Connection connection, int genNo) throws SQLException {
        //INSERT Customer Package
        String sql = "INSERT INTO \n"
                + "				customer_package \n"
                + "				(customer_package_unit,\n"
                + "				customer,\n"
                + "				package,\n"
                + "				fee,\n"
                + "				payble_amount,\n"
                + "				balance,\n"
                + "				total_min_exam,\n"
                + "				total_min_trial,\n"
                + "				status\n"
                + "				) VALUES (\n"
                + "                                       '" + genNo + "',\n"
                + "                                       '" + custCode + "',\n"
                + "                                       '" + vp.getCode() + "',\n"
                + "                                       '" + vp.getAmount() + "',\n"
                + "                                       '" + vp.getAmount() + "',\n"
                + "                                       '" + vp.getAmount() + "',\n"
                + "                                       '" + vp.getMinForExam() + "',\n"
                + "                                       '" + vp.getMinForTrial() + "',\n"
                + "                                       '" + CustomerPackageStatus.ACTIVE + "'\n"
                + "				)";
        PreparedStatement preparedStmt = connection.prepareStatement(sql, 1);
        preparedStmt.executeUpdate();
        ResultSet resTrans;
        resTrans = preparedStmt.getGeneratedKeys();
        if (resTrans.next()) {
            return resTrans.getInt(1);
        } else {
            return 0;
        }
    }

    public static void updateCPU(VehiclePackage vp, VehiclePackage extVp, String cpu, Connection connection) throws SQLException {
        String sql = "UPDATE \n"
                + "				`customer_package_unit` \n"
                + "SET \n"
                + "				`min_exam` = `min_exam` + '" + vp.getMinForExam() + "' - '" + extVp.getMinForExam() + "', \n"
                + "				`min_trial` = `min_trial` + '" + vp.getMinForTrial() + "' - '" + extVp.getMinForTrial() + "', \n"
                + "				`amount` = `amount` + '" + vp.getAmount() + "' - '" + extVp.getAmount() + "', \n"
                + "				`total_payble` = `total_payble` + '" + vp.getAmount() + "' - '" + extVp.getAmount() + "', \n"
                + "				`balance` = `balance` + '" + vp.getAmount() + "' - '" + extVp.getAmount() + "' \n"
                + "WHERE  \n"
                + "				`index_no`='" + cpu + "';";
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.executeUpdate();
    }

    public static void updateCPU(Charge charge, Connection connection) throws SQLException {
        String sql = "";
        if (charge.getChargeRebit().equals(OtherChargeType.CHARGE)) {
            sql = "UPDATE \n"
                    + "				`customer_package_unit` \n"
                    + "SET \n"
                    + "				`min_exam` = `min_exam` + '" + charge.getMinExam() + "', \n"
                    + "				`min_trial` = `min_trial` + '" + charge.getMinTrial() + "', \n"
                    + "				`amount` = `amount` + '" + charge.getAmount() + "', \n"
                    + "				`total_payble` = `total_payble` + '" + charge.getAmount() + "', \n"
                    + "				`balance` = `balance` + '" + charge.getAmount() + "' \n"
                    + "WHERE  \n"
                    + "				`index_no`='" + charge.getCpu() + "'";
        } else if (charge.getChargeRebit().equals(OtherChargeType.REBIT)) {
            sql = "UPDATE \n"
                    + "				`customer_package_unit` \n"
                    + "SET \n"
                    + "				`min_exam` = `min_exam` - '" + charge.getMinExam() + "', \n"
                    + "				`min_trial` = `min_trial` - '" + charge.getMinTrial() + "', \n"
                    + "				`amount` = `amount` - '" + charge.getAmount() + "', \n"
                    + "				`total_payble` = `total_payble` - '" + charge.getAmount() + "', \n"
                    + "				`balance` = `balance` - '" + charge.getAmount() + "' \n"
                    + "WHERE  \n"
                    + "				`index_no`='" + charge.getCpu() + "'";
        }
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.executeUpdate();
    }

    public static void updateCPU(VoucherVDO voucherVDO, Connection connection) throws SQLException {
        String sql;

        sql = "UPDATE \n"
                + "				`customer_package_unit` \n"
                + "SET \n"
                + "				`total_payble` = `total_payble` - '" + voucherVDO.getPayAmount() + "', \n"
                + "				`paid` = `paid` + '" + voucherVDO.getPayAmount() + "', \n"
                + "				`balance` = `balance` - '" + voucherVDO.getPayAmount() + "' \n"
                + "WHERE  \n"
                + "				`index_no`='" + voucherVDO.getCpu() + "'";

        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.executeUpdate();
    }

    public static void cancelCustomerPackage(String extPackage, String cpu, Connection connection) throws SQLException {
        String sql = "UPDATE \n"
                + "				`customer_package` \n"
                + "SET \n"
                + "				`status` = '" + CustomerPackageStatus.CANCEL + "' \n"
                + "WHERE  \n"
                + "				customer_package.customer_package_unit = '" + cpu + "' AND \n"
                + "				customer_package.package = '" + extPackage + "' AND \n"
                + "				customer_package.`status` = '" + CustomerPackageStatus.ACTIVE + "'";
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.executeUpdate();
    }

}
