/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.transaction;

import com.rowi.lms.common.status.AccountTransactionStatus;
import com.rowi.lms.modle.transaction.AccountTrans;
import com.rowi.lms.modle.vdo.OtherChargeVDO;
import com.rowi.lms.modle.vdo.PaymentMethodVDO;
import com.rowi.lms.modle.vdo.VoucherVDO;
import java.util.ArrayList;

/**
 *
 * @author ROSHAN
 */
public class AccountTransactionManager {

    public static ArrayList<AccountTrans> voucherTransaction(VoucherVDO voucherVDO) {

        ArrayList<PaymentMethodVDO> paymentMethodVDOs = voucherVDO.getPaymentMethodVDOs();

//        ArrayList<AccountTrans> accountTranses = null;
        ArrayList<AccountTrans> accountTranses = new ArrayList<AccountTrans>();
        //AccountSetting Transaction
        for (String account : voucherVDO.getAccounts()) {
            AccountTrans accountTrans = new AccountTrans();
            accountTrans.setTransactionDate(voucherVDO.getVoucherDate());
            accountTrans.setCostCode(voucherVDO.getBranch());
            accountTrans.setAccountSetting(null);
            accountTrans.setDescription(voucherVDO.getVoucherType() + " For - Voucher No : ");
            accountTrans.setAccount(account);

            if (voucherVDO.getVoucherType().startsWith("RECEIPT")) {
                accountTrans.setCreditAmount(Double.parseDouble(voucherVDO.getPayAmount()));
                accountTrans.setDebitAmount(0.00);
            } else {
                accountTrans.setCreditAmount(0.00);
                accountTrans.setDebitAmount(Double.parseDouble(voucherVDO.getPayAmount()));
            }
            accountTrans.setTransactionType(voucherVDO.getVoucherType());
            accountTrans.setType("AUTO");
            accountTrans.setStatus(AccountTransactionStatus.ACTIVE);
            accountTrans.setUser(voucherVDO.getUser());
            
            accountTranses.add(accountTrans);
        }

        //PaymentSetting Transaction
        for (PaymentMethodVDO paymentMethodVDO : paymentMethodVDOs) {
            AccountTrans accountTrans = new AccountTrans();
            accountTrans.setTransactionDate(voucherVDO.getVoucherDate());
            accountTrans.setCostCode(voucherVDO.getBranch());
            accountTrans.setAccountSetting(null);
            accountTrans.setDescription("Cash payment " + voucherVDO.getVoucherType() + " For - Voucher No : ");
            accountTrans.setAccount("111"); //111 - main cash account

            if (voucherVDO.getVoucherType().startsWith("RECEIPT")) {
                accountTrans.setCreditAmount(0.00);
                accountTrans.setDebitAmount(Double.parseDouble(voucherVDO.getPayAmount()));
            } else {
                accountTrans.setCreditAmount(Double.parseDouble(voucherVDO.getPayAmount()));
                accountTrans.setDebitAmount(0.00);
            }

            accountTrans.setTransactionType(voucherVDO.getVoucherType());
            accountTrans.setType("AUTO");
            accountTrans.setStatus(AccountTransactionStatus.ACTIVE);
            accountTrans.setUser(voucherVDO.getUser());
            accountTranses.add(accountTrans);
        }

        return accountTranses;
    }

    public static ArrayList<AccountTrans> otherChargeTransaction(OtherChargeVDO otherChargeVDO) {

        ArrayList<AccountTrans> accountTranses = null;
        //AccountSetting Transaction

        return accountTranses;
    }

}
