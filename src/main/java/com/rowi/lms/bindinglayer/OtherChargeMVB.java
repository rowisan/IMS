/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.bindinglayer;

import com.rowi.lms.common.SystemCharges;
import com.rowi.lms.common.status.SettlementStatus;
import com.rowi.lms.modle.Charge;
import com.rowi.lms.modle.Settlement;
import com.rowi.lms.modle.dto.TransactionDTO;
import com.rowi.lms.modle.transaction.AccountTrans;
import com.rowi.lms.modle.transaction.Transaction;
import com.rowi.lms.modle.transaction.TransactionHistory;
import com.rowi.lms.modle.vdo.OtherChargeVDO;
import com.rowi.lms.services.OtherChargeSER;
import com.rowi.lms.servlets.ChargeSER;
import com.rowi.lms.transaction.AccountTransactionManager;
import com.rowi.lms.transaction.TransactionManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ROSHAN
 */
public class OtherChargeMVB {

    public static String save(OtherChargeVDO otherChargeVDO) throws SQLException {
        double amount = Double.parseDouble(otherChargeVDO.getAmount());
        String remark = otherChargeVDO.getRemark();
        String cpu = otherChargeVDO.getCpu();
        String minExam = otherChargeVDO.getMinExam();
        String minTrial = otherChargeVDO.getMinTrial();

        Charge charge = ChargeSER.getCharge(otherChargeVDO.getCharge());
        charge.setAmount(amount);
        charge.setRemark(remark);
        charge.setCpu(cpu);
        charge.setMinExam(Double.parseDouble(minExam));
        charge.setMinTrial(Double.parseDouble(minTrial));

        Settlement settlement = new Settlement();
        settlement.setCustomerPackageUnit(charge.getCpu());
        settlement.setCharge(charge.getCode());
        settlement.setRefType(SystemCharges.OTHER_CHARGE);
        settlement.setAmount(charge.getAmount());
        settlement.setPrivSettle(0.00);
        settlement.setPrivSettleRef("");
        settlement.setPaid(0.00);
        settlement.setBalance(charge.getAmount());
        settlement.setMinExam(charge.getMinExam());
        settlement.setMinTrial(charge.getMinTrial());
        if (charge.getPrintOrder() != null || !charge.getPrintOrder().equals("")) {
            int printOrder = Integer.parseInt(charge.getPrintOrder());
            settlement.setPrintOrder(printOrder);
        }
        settlement.setStatus(SettlementStatus.ACTIVE);

        Transaction transaction = TransactionManager.getTransaction(otherChargeVDO, SettlementStatus.ACTIVE);
        ArrayList<TransactionHistory> transactionHistory = TransactionManager.getTransactionHistory(otherChargeVDO, "INSERT");
        ArrayList<AccountTrans> accountTranses = AccountTransactionManager.otherChargeTransaction(otherChargeVDO);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransaction(transaction);
        transactionDTO.setTransHistory(transactionHistory);
        transactionDTO.setAccountTranses(accountTranses);

        String msg = OtherChargeSER.save(charge, settlement, transactionDTO);
        return msg;
    }

}
