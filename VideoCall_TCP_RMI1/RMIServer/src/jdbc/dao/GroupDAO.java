/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.dao;

import static jdbc.dao.DAO.con;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import static jdbc.dao.DAO.con;
import model.Group;
import model.GroupMember;
import model.PrivateCall;
import model.User;

/**
 *
 * @author Little Coconut
 */
public class GroupDAO extends DAO {
    
    public GroupDAO() {
        super();
    }
    
    public ArrayList<Group> getListGroups(Integer userId) {
        ArrayList<Group> result = new ArrayList<Group>();
        String sql = "{call get_list_groups(?)}";
        try {
            CallableStatement ps = con.prepareCall(sql);
            ps.setInt(1, userId);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Group g = new Group();
                g.setId(rs.getInt("id"));
                g.setName(rs.getString("name"));
                g.setCreatedAt(rs.getDate("createdAt"));
                g.setCreatedBy(rs.getInt("createdBy"));
                result.add(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean leaveGroup(GroupMember member) {
        boolean result = true;
        String sql = "{call delete_group_member(?,?)}";
        try {
            CallableStatement ps = con.prepareCall(sql);
            ps.setInt(1, member.getUserId());
            ps.setInt(2, member.getGroupId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
    
    public boolean deleteGroup(int groupId) {
        boolean result = true;
        String sql = "{call delete_group (?)}";
        try {
            CallableStatement ps = con.prepareCall(sql);
            ps.setInt(1, groupId);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
    
    public boolean createGroup(String groupName, int userId) {
        java.sql.Date time = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        boolean result = true;
        String sql = "{call create_group (?,?,?)}"; //groupname, userid, date
        String sql2 = "{call create_new_groupmember (?,?,?)}"; // groupId - userId - date
        try {
            CallableStatement ps = con.prepareCall(sql);
            ps.setString(1, groupName);
            ps.setInt(2, userId);
            ps.setDate(3, time);
//            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int groupId = rs.getInt("LAST_INSERT_ID()");
                CallableStatement ps2 = con.prepareCall(sql2);
                ps2.setInt(1, groupId);
                ps2.setInt(2, userId);
                ps2.setDate(3, time);
                ps2.execute();
                System.out.println(groupId + " + " + userId + " + " + time);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
    
    public ArrayList<User> getListMemberInGroup(int groupId) {
        ArrayList<User> result = new ArrayList<User>();
        UserDAO ud = new UserDAO();
        String sql = "SELECT  * FROM tblgroupmember WHERE groupId = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, groupId);            
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) { 
                int userId = rs.getInt("userId");
                User u = ud.getUserById(userId);
                result.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
//    public User getUserById(int userId) {
//        String sql = "SELECT  * FROM tbluser WHERE id = ?";
//        try {
//            PreparedStatement ps = con.prepareStatement(sql);
//            ps.setInt(1, userId); 
//
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                User user = new User();
//                user.setId(rs.getInt("id")); 
//                user.setName(rs.getString("name"));
//                user.setUsername(rs.getString("username"));
//                user.setAddress(rs.getString("address"));
//                return user;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}
