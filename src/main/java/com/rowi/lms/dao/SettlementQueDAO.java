/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.dao;

import com.rowi.lms.common.SystemMessage.CommonLogger;
import com.rowi.lms.common.TransactionType;
import com.rowi.lms.common.status.SettlementStatus;
import com.rowi.lms.modle.Settlement;
import com.rowi.lms.modle.dto.SettlementDTO;
import com.rowi.lms.modle.vdo.VoucherVDO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ROSHAN
 */
public class SettlementQueDAO {

    public static void doSettle(double amount, String cpu, String voucherNo, Connection connection) throws SQLException {
        if (cpu != null && !cpu.equals("")) {
            ArrayList<Settlement> settlements = SettlementDAO.getSettlements(cpu, connection);
            ArrayList<SettlementDTO> settleds = new ArrayList<SettlementDTO>();
            for (Settlement settlement : settlements) {
                if (settlement.getStatus().equals(SettlementStatus.ACTIVE) && amount > 0) {
                    SettlementDTO settlementDTO = new SettlementDTO();
                    settlementDTO.setIndexNo(settlement.getIndexNo());
                    if (amount >= settlement.getBalance()) {
                        amount = amount - settlement.getBalance();
                        settlementDTO.setSettledAmount(settlement.getBalance());
                        settlementDTO.setPaid(settlement.getPaid() + settlement.getBalance());
                        settlementDTO.setBalance(0.00);
                        settlementDTO.setStatus(SettlementStatus.SETTLED);
                    } else {
                        settlementDTO.setSettledAmount(amount);
                        settlementDTO.setPaid(settlement.getPaid() + amount);
                        settlementDTO.setBalance(settlement.getBalance() - amount);
                        settlementDTO.setStatus(SettlementStatus.ACTIVE);
                        amount = 0.00;
                    }
                    settleds.add(settlementDTO);
                }
            }
            if (settleds != null) {
                settled(settleds, voucherNo, TransactionType.RECEIPT, connection);
            }
        }
    }

    private static void settled(ArrayList<SettlementDTO> settleds, String referenceNo, String referenceType, Connection connection) throws SQLException {
        String sql2 = "INSERT INTO \n"
                + "				`settlement_history` \n"
                + "				(`settlement`, \n"
                + "				`reference`, \n"
                + "				`reference_type`, \n"
                + "				`settlement_amount`, \n"
                + "				`status`) \n"
                + "VALUES ";
        int i = 0;
        for (SettlementDTO settled : settleds) {
            String sql = "UPDATE \n"
                    + "				settlement \n"
                    + "SET \n"
                    + "				settlement.`paid` = '" + settled.getPaid() + "',\n"
                    + "				settlement.`balance` = '" + settled.getBalance() + "',\n"
                    + "				settlement.`status` = '" + settled.getStatus() + "' \n"
                    + "WHERE  \n"
                    + "				settlement.index_no = '" + settled.getIndexNo() + "' ";
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.executeUpdate();

            if (i > 0) {
                sql2 = sql2 + ",\n";
            }
            sql2 = sql2 + "			('" + settled.getIndexNo() + "', \n"
                    + "				'" + referenceNo + "', \n"
                    + "				'" + referenceType + "', \n"
                    + "				'" + settled.getSettledAmount() + "', \n"
                    + "				'" + SettlementStatus.ACTIVE + "')";
            i++;
        }
        if (settleds != null) {
            PreparedStatement preparedStmt = connection.prepareStatement(sql2);
            preparedStmt.executeUpdate();
        }
    }

}
