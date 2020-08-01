/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.services;

import com.rowi.lms.common.SystemMessage.CommonLogger;
import com.rowi.lms.common.SystemMessageType;
import com.rowi.lms.common.VoucherType;
import com.rowi.lms.dao.PackageDAO;
import com.rowi.lms.dao.SettlementQueDAO;
import com.rowi.lms.dao.TransactionDAO;
import com.rowi.lms.dao.VoucherDAO;
import com.rowi.lms.modle.Voucher;
import com.rowi.lms.util.DBConnection;
import com.rowi.lms.modle.dto.TransactionDTO;
import com.rowi.lms.modle.vdo.VoucherVDO;
import com.rowi.lms.report.ReceiptREP;
import com.rowi.lms.util.Utilitys;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ROSHAN
 */
public class VoucherSER {

    private static String msg;

    public static String save(
            Voucher voucher,
            TransactionDTO transactionDTO,
            VoucherVDO voucherVDO) {
        Connection connection = DBConnection.getConnection();
        try {
            connection.setAutoCommit(false);
            String voucherNo = VoucherDAO.save(voucher, connection);
            voucherNo = Utilitys.setIDFormat(voucherNo);
            if (voucherNo != null) {
                PackageDAO.updateCPU(voucherVDO, connection);
                SettlementQueDAO.doSettle(Double.parseDouble(voucherVDO.getPayAmount()), voucherVDO.getCpu(), voucherNo, connection);
                TransactionDAO.doCommonTransaction(transactionDTO, connection, voucherNo);
                connection.commit();
                if (voucher.getType().startsWith("RECEIPT")) {
                    ReceiptREP.main(voucher, voucherNo);
                }
                msg = "Customer payment Successful.~" + SystemMessageType.SUCCESS;
                CommonLogger.log("DEBUG", VoucherSER.class.getName(), "save() >> connection.commit()");
            } else {
                connection.rollback();
                msg = "Customer payment Error.~" + SystemMessageType.ERROR;
                CommonLogger.log("ERROR", VoucherSER.class.getName(), "save() >> connection.rollback()");
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
                CommonLogger.handle(ex, VoucherSER.class.getName(), "save() >> connection.rollback()");
            } catch (SQLException ex1) {
                CommonLogger.handle(ex, VoucherSER.class.getName(), "save() >> Transaction rollback error.");
                Logger.getLogger(VoucherSER.class.getName()).log(Level.SEVERE, null, ex1);
            }
            msg = "Customer payment Error.~" + SystemMessageType.ERROR;
            Logger.getLogger(VoucherSER.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnection.releasConnection(connection);
        return msg;
    }

}
