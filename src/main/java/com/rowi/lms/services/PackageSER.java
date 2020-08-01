/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.services;

import com.rowi.lms.action.impl.CustomerController;
import com.rowi.lms.common.SystemCharges;
import com.rowi.lms.common.SystemMessageType;
import com.rowi.lms.common.SettlementType;
import com.rowi.lms.common.SystemMessage.CommonLogger;
import com.rowi.lms.common.status.SettlementStatus;
import com.rowi.lms.dao.PackageDAO;
import com.rowi.lms.dao.SettlementDAO;
import com.rowi.lms.modle.Customer;
import com.rowi.lms.modle.CustomerPackage;
import com.rowi.lms.modle.Settlement;
import com.rowi.lms.modle.VehiclePackage;
import com.rowi.lms.util.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ROSHAN
 */
public class PackageSER {

    private static String msg;

    public static CustomerPackage getCustomerPackage(String custCode) throws SQLException {
        return PackageDAO.getCustomerPackage(custCode);
    }

    public static ArrayList<CustomerPackage> getCustomerPackages(String custCode) throws SQLException {
        return PackageDAO.getCustomerPackages(custCode);
    }

    public static VehiclePackage getPackageInfo(String Code) throws SQLException {
        return PackageDAO.getPackageInfo(Code);
    }

    public static ArrayList<VehiclePackage> getList() throws SQLException {
        return PackageDAO.getList();
    }

    public static Customer getCustomer(String code) throws SQLException {
        return PackageDAO.getCustomer(code);
    }

    public static String save(String packageCode, String custCode) {
        Connection connection = DBConnection.getConnection();
        VehiclePackage vp = null;
        try {
            vp = PackageDAO.getPackage(packageCode, connection);
        } catch (SQLException ex) {
            CommonLogger.handle(ex, PackageSER.class.getName(), "save()");
            Logger.getLogger(PackageSER.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            connection.setAutoCommit(false);
            //INSERT customer_package_unit AND customer_package
            int cpu = PackageDAO.save(vp, custCode, connection);

            //INSERT customer_package
            int cpID = PackageDAO.insertCustomerPackage(vp, custCode, connection, cpu);

            //INSERT settlement
            Settlement settlement = new Settlement();
            settlement.setCustomerPackageUnit(cpu + "");
            settlement.setCharge(SystemCharges.DEFAULT_PACKAGE_AMOUNT);
            settlement.setReferanceNo(cpID + "");
            settlement.setRefType(SettlementType.PACKAGE);
            settlement.setAmount(vp.getAmount());
            settlement.setPrivSettle(0.00);
            settlement.setPrivSettleRef("");
            settlement.setPaid(0.00);
            settlement.setBalance(vp.getAmount());
            settlement.setMinExam(vp.getMinForExam());
            settlement.setMinTrial(vp.getMinForTrial());
            //Package Print Order = 10
            settlement.setPrintOrder(10);
            settlement.setStatus(SettlementStatus.ACTIVE);
            SettlementDAO.insertSettlement(settlement, connection);

            connection.commit();
            msg = "Customer saved Successfuly.~" + SystemMessageType.SUCCESS;
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                CommonLogger.handle(ex, PackageSER.class.getName(), "save()");
                Logger.getLogger(PackageSER.class.getName()).log(Level.SEVERE, null, ex1);
            }
            CommonLogger.handle(ex, PackageSER.class.getName(), "save()");
            msg = "Customer saving Error.~" + SystemMessageType.ERROR;
            Logger.getLogger(PackageSER.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnection.releasConnection(connection);
        return msg;
    }

    public static String update(String packageCode, String custCode, String cpu, String extPackage, String ExtPackageId) {
        Connection connection = DBConnection.getConnection();
        VehiclePackage vp = null;
        VehiclePackage extVp = null;
        try {
            vp = PackageDAO.getPackage(packageCode, connection);
            extVp = PackageDAO.getPackage(extPackage, connection);
        } catch (SQLException ex) {
            CommonLogger.handle(ex, PackageSER.class.getName(), "update()");
            Logger.getLogger(PackageSER.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            connection.setAutoCommit(false);
            //Update customer_package_unit
            PackageDAO.updateCPU(vp, extVp, cpu, connection);

            //Cancel customer_package
            PackageDAO.cancelCustomerPackage(extPackage, cpu, connection);
            //Cancel settlement
            String ref = ExtPackageId;
            String refType = SettlementType.PACKAGE;
            Settlement prvSettlement = SettlementDAO.getSettlement(ref, refType, cpu, connection);
            SettlementDAO.cancelSettlement(prvSettlement.getIndexNo(), connection);

            //INSERT customer_package
            int cpID = PackageDAO.insertCustomerPackage(vp, custCode, connection, Integer.parseInt(cpu));
            //INSERT settlement
            Settlement settlement = new Settlement();
            settlement.setCustomerPackageUnit(cpu + "");
            settlement.setCharge(SystemCharges.DEFAULT_PACKAGE_AMOUNT);
            settlement.setReferanceNo(cpID + "");
            settlement.setRefType(SettlementType.PACKAGE);
            settlement.setAmount(vp.getAmount());
            settlement.setPrivSettle(0.00);
            settlement.setPrivSettleRef(prvSettlement.getIndexNo() + "");
            settlement.setPaid(0.00);
            settlement.setBalance(vp.getAmount());
            settlement.setMinExam(vp.getMinForExam());
            settlement.setMinTrial(vp.getMinForTrial());
            settlement.setStatus(SettlementStatus.ACTIVE);
            SettlementDAO.insertSettlement(settlement, connection);

            //Settle with rem value (Using settlement que)
//            TODO : call to settlement que with 'rem'
            connection.commit();
            msg = "Customer update Successfuly.~" + SystemMessageType.SUCCESS;
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                CommonLogger.handle(ex, PackageSER.class.getName(), "update()");
                Logger.getLogger(PackageSER.class.getName()).log(Level.SEVERE, null, ex1);
            }
            CommonLogger.handle(ex, PackageSER.class.getName(), "update()");
            msg = "Customer update Error.~" + SystemMessageType.ERROR;
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnection.releasConnection(connection);
        return msg;
    }

}
