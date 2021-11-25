/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.dao;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import static jdbc.dao.DAO.con;
import model.Friend;
import model.Group;
import model.GroupMember;
import model.User;

/**
 *
 * @author Little Coconut
 */
public class GroupMemberDAO2 extends DAO {

    public GroupMemberDAO2() {
        super();
    }

    public boolean refuseMember(Integer id) {
        boolean result = true;
        String sql = "{call refuse_member(?)}";
        try {
            CallableStatement ps = con.prepareCall(sql);
            ps.setInt(1, id);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public boolean acceptMember(Integer id) { 
        String sql = "{call accept_member(?)}";
        java.sql.Date time = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        boolean result = true;
        try {
            CallableStatement ps = con.prepareCall(sql);
            ps.setInt(1, id);
            ps.setDate(2, time);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public boolean addMember(int userId, int groupId) { 
        String sql = "{call add_member_to_group(?,?,?)}";
        java.sql.Date time = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        boolean result = true;
        try {
            CallableStatement ps = con.prepareCall(sql);
            ps.setInt(1, userId);
            ps.setInt(2, groupId);
            ps.setDate(3, time);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
    
    public boolean joinGroup(int userId, int groupId) { 
        String sql = "{call join_group(?,?,?)}";
        java.sql.Date time = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        boolean result = true;
        try {
            CallableStatement ps = con.prepareCall(sql);
            ps.setInt(1, userId);
            ps.setInt(2, groupId);
            ps.setDate(3, time);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
    
    public ArrayList<GroupMember> getListRequestJoin(Integer groupId) {
        ArrayList<GroupMember> result = new ArrayList<GroupMember>();
        String sql = "{call get_list_request_join(?)}";
        try {
            CallableStatement ps = con.prepareCall(sql);
            ps.setInt(1, groupId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GroupMember g = new GroupMember();
                g.setId(rs.getInt("id"));
                g.setCreatedAt(rs.getDate("createdAt"));
//                g.setStatus(rs.getInt("status"));
                g.setUserId(rs.getInt("userId"));
                result.add(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
