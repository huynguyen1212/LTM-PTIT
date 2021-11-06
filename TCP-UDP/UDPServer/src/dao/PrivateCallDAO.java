/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.DAO.con;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import model.PrivateCall;
import model.User;

/**
 *
 * @author Little Coconut
 */
public class PrivateCallDAO extends DAO{

    public PrivateCallDAO() {
        super();
    }

    public int createPrivateCall(PrivateCall privateCall) {
        int result = -1;
        java.sql.Timestamp createdAt = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
//        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        String sql = "{call create_private_call(?,?,?)}";
        try {
            CallableStatement ps = con.prepareCall(sql);
            ps.setTimestamp(3, createdAt);
            ps.setInt(1, privateCall.getSourceId());
            ps.setInt(2, privateCall.getTargetId());  
            ResultSet rs = ps.executeQuery();
            while (rs.next()) { 
                result = rs.getInt("LAST_INSERT_ID()");
            }
//            System.out.println("udp return id of new private call " + result);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return result;
    }
}
