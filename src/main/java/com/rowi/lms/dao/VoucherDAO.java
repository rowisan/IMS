/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.dao;

import com.rowi.lms.modle.Voucher;
import com.rowi.lms.util.Utilitys;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ROSHAN
 */
public class VoucherDAO {

    public static String save(Voucher voucher, Connection connection) throws SQLException {
        String sql = "INSERT INTO voucher (cpu, customer, voucher_date, branch, document_no, remark, amount, pay_term, type, user, status) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement st = connection.prepareStatement(sql, 1);
        st.setString(1, voucher.getCpu());
        st.setString(2, voucher.getCustomer());
        st.setDate(3, (Date) voucher.getVoucherDate());
        st.setString(4, voucher.getBranch());
        st.setString(5, voucher.getDocumentNo());
        st.setString(6, voucher.getRemark());
        st.setString(7, voucher.getAmount());
        st.setString(8, voucher.getPayTerm());
        st.setString(9, voucher.getType());
        st.setString(10, voucher.getUser());
        st.setString(11, voucher.getStatus());
        st.executeUpdate();
        ResultSet resultSet;
        resultSet = st.getGeneratedKeys();
        if (resultSet.next()) {
            return Utilitys.setIDFormat(resultSet.getString(1));
        } else {
            return null;
        }
    }

}
