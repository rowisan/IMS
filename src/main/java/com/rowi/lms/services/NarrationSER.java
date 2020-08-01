/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.services;

import com.rowi.lms.dao.NarrationDAO;
import com.rowi.lms.modle.Narration;
import com.rowi.lms.util.DBConnection;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author ROSHAN
 */
public class NarrationSER {

    public static ArrayList<Narration> getList(String type) {
        Connection connection = DBConnection.getConnection();
        ArrayList<Narration> narrations = NarrationDAO.getList(type);
        DBConnection.releasConnection(connection);
        return narrations;
    }

}
