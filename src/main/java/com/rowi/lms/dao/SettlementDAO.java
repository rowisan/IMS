/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.dao;

import com.rowi.lms.common.OtherChargeType;
import com.rowi.lms.common.SettlementType;
import com.rowi.lms.common.status.ChargeStatus;
import com.rowi.lms.common.status.CustomerPackageStatus;
import com.rowi.lms.common.status.CustomerStatus;
import com.rowi.lms.common.status.SettlementStatus;
import com.rowi.lms.modle.Settlement;
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
public class SettlementDAO {

    public static void insertSettlement(Settlement settlement, Connection connection) throws SQLException {
        String sql = "INSERT INTO \n"
                + "				settlement \n"
                + "				(customer_package_unit,\n"
                + "				charge,\n"
                + "				referance_no,\n"
                + "				ref_type,\n"
                + "				amount,\n"
                + "				priv_settle,\n"
                + "				priv_settle_ref,\n"
                + "				paid,\n"
                + "				balance,\n"
                + "				min_exam,\n"
                + "				min_trial,\n"
                + "				print_order,\n"
                + "				status\n"
                + "				) VALUES (\n"
                + "                                       '" + settlement.getCustomerPackageUnit() + "',\n"
                + "                                       '" + settlement.getCharge() + "',\n"
                + "                                       '" + settlement.getReferanceNo() + "',\n"
                + "                                       '" + settlement.getRefType() + "',\n"
                + "                                       '" + settlement.getAmount() + "',\n"
                + "                                       '" + settlement.getPrivSettle() + "',\n"
                + "                                       '" + settlement.getPrivSettleRef() + "',\n"
                + "                                       '" + settlement.getPaid() + "',\n"
                + "                                       '" + settlement.getBalance() + "',\n"
                + "                                       '" + settlement.getMinExam() + "',\n"
                + "                                       '" + settlement.getMinTrial() + "',\n"
                + "                                       '" + settlement.getPrintOrder() + "',\n"
                + "                                       '" + CustomerPackageStatus.ACTIVE + "'\n"
                + "				)";
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.execute();
    }

    public static Settlement getSettlement(String ref, String refType, String cpu, Connection connection) throws SQLException {
        String sql = "SELECT \n"
                + "				* \n"
                + "FROM \n"
                + "				settlement \n"
                + "WHERE  \n"
                + "				settlement.customer_package_unit = '" + cpu + "' AND \n"
                + "				settlement.referance_no = '" + ref + "' AND \n"
                + "				settlement.ref_type = '" + refType + "' AND \n"
                + "				settlement.`status` = '" + SettlementStatus.ACTIVE + "' ";
        Settlement settlement = new Settlement();
        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            settlement.setIndexNo(resultSet.getInt("index_no"));
            settlement.setCustomerPackageUnit(resultSet.getString("customer_package_unit"));
            settlement.setCharge(resultSet.getString("charge"));
            settlement.setReferanceNo(resultSet.getString("referance_no"));
            settlement.setRefType(resultSet.getString("ref_type"));
            settlement.setAmount(resultSet.getDouble("amount"));
            settlement.setPrivSettle(resultSet.getDouble("priv_settle"));
            settlement.setPrivSettleRef(resultSet.getString("priv_settle_ref"));
            settlement.setPaid(resultSet.getDouble("paid"));
            settlement.setBalance(resultSet.getDouble("balance"));
            settlement.setMinExam(resultSet.getDouble("min_exam"));
            settlement.setMinTrial(resultSet.getDouble("min_trial"));
            settlement.setStatus(resultSet.getString("status"));
        }
        return settlement;
    }

    public static ArrayList<Settlement> getSettlements(String cpu, Connection connection) throws SQLException {
        String sql = "SELECT \n"
                + "				settlement.index_no,\n"
                + "				package.description,\n"
                + "				settlement.amount, \n"
                + "				settlement.paid, \n"
                + "				settlement.balance,\n"
                + "				'Default Package Amount' as remark,\n"
                + "				settlement.print_order,\n"
                + "				settlement.referance_no,\n"
                + "				settlement.status\n"
                + "FROM \n"
                + "				settlement \n"
                + "				LEFT JOIN customer_package ON customer_package.customer_package_unit= settlement.customer_package_unit AND settlement.ref_type = '" + SettlementType.PACKAGE + "'\n"
                + "				LEFT JOIN package ON package.code = customer_package.package\n"
                + "WHERE \n"
                + "				settlement.customer_package_unit = '" + cpu + "' AND\n"
                + "				settlement.ref_type = '" + SettlementType.PACKAGE + "' AND\n"
                + "				settlement.`status` IN ('" + SettlementStatus.ACTIVE + "', '" + SettlementStatus.SETTLED + "') AND\n"
                + "				customer_package.`status` = '" + CustomerStatus.ACTIVE + "'\n"
                + "UNION\n"
                + "\n"
                + "SELECT \n"
                + "				settlement.index_no,\n"
                + "				charge.description , \n"
                + "				settlement.amount, \n"
                + "				settlement.paid, \n"
                + "				settlement.balance,\n"
                + "				other_charge.remark,\n"
                + "				settlement.print_order,\n"
                + "				settlement.referance_no,\n"
                + "				settlement.status\n"
                + "FROM \n"
                + "				settlement \n"
                + "				LEFT JOIN charge ON charge.code = settlement.charge AND settlement.ref_type = '" + SettlementType.OTHER_CHARGE + "'\n"
                + "				LEFT JOIN other_charge ON other_charge.index_no = settlement.referance_no AND settlement.ref_type = '" + SettlementType.OTHER_CHARGE + "'\n"
                + "WHERE \n"
                + "				settlement.customer_package_unit = '" + cpu + "' AND\n"
                + "				settlement.ref_type = '" + SettlementType.OTHER_CHARGE + "' AND\n"
                + "				charge.charge_rebit = '" + OtherChargeType.CHARGE + "' AND\n"
                + "				settlement.`status` IN ('" + SettlementStatus.ACTIVE + "', '" + SettlementStatus.SETTLED + "') AND\n"
                + "				charge.`status` = '" + ChargeStatus.ACTIVE + "'\n"
                + "ORDER BY print_order ASC, referance_no ASC";
        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<Settlement> settlements = new ArrayList<Settlement>();
        while (resultSet.next()) {
            Settlement settlement = new Settlement();
            settlement.setIndexNo(resultSet.getInt("index_no"));
            settlement.setDescription(resultSet.getString("description"));
            settlement.setAmount(resultSet.getDouble("amount"));
            settlement.setPaid(resultSet.getDouble("paid"));
            settlement.setBalance(resultSet.getDouble("balance"));
            settlement.setCharge(resultSet.getString("remark"));
            settlement.setStatus(resultSet.getString("status"));
            settlements.add(settlement);
        }
        return settlements;
    }

    public static void cancelSettlement(int indexNo, Connection connection) throws SQLException {
        String sql = "UPDATE \n"
                + "				settlement \n"
                + "SET \n"
                + "				settlement.`status` = '" + CustomerPackageStatus.CANCEL + "' \n"
                + "WHERE  \n"
                + "				settlement.index_no = '" + indexNo + "' ";
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.executeUpdate();
    }

}
