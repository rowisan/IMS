/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.services;

import com.rowi.lms.common.SystemCharges;
import com.rowi.lms.common.SystemMessageType;
import com.rowi.lms.common.SystemMessage.CommonLogger;
import com.rowi.lms.common.status.SettlementStatus;
import com.rowi.lms.dao.OtherChargesDAO;
import com.rowi.lms.dao.PackageDAO;
import com.rowi.lms.dao.SettlementDAO;
import com.rowi.lms.dao.TransactionDAO;
import com.rowi.lms.modle.Charge;
import com.rowi.lms.modle.Settlement;
import com.rowi.lms.modle.dto.TransactionDTO;
import com.rowi.lms.modle.transaction.Transaction;
import com.rowi.lms.util.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ROSHAN
 */
public class OtherChargeSER {

    private static String msg;

    public static String save(Charge charge, Settlement settlement, TransactionDTO transactionDTO) {
        Connection connection = DBConnection.getConnection();

        try {
            connection.setAutoCommit(false);

            //Update CPU
            PackageDAO.updateCPU(charge, connection);

            //Insert Other_charge
            int chargeID = OtherChargesDAO.save(charge, connection);

            //INSERT settlement
            settlement.setReferanceNo(String.valueOf(chargeID));
            SettlementDAO.insertSettlement(settlement, connection);

            //INSERT Transactions
            TransactionDAO.doCommonTransaction(transactionDTO, connection, String.valueOf(chargeID));

            connection.commit();
            msg = "Inserting other charge Successfuly.~" + SystemMessageType.SUCCESS;
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                CommonLogger.handle(ex, OtherChargeSER.class.getName(), "save()");
                Logger.getLogger(OtherChargeSER.class.getName()).log(Level.SEVERE, null, ex1);
            }
            CommonLogger.handle(ex, OtherChargeSER.class.getName(), "save()");
            msg = "Inserting other charge Error.~" + SystemMessageType.ERROR;
            Logger.getLogger(OtherChargeSER.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnection.releasConnection(connection);

        return msg;
    }

}
