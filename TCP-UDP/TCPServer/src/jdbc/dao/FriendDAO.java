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
import model.Customer;
import model.Friend;
import model.User;

/**
 *
 * @author Little Coconut
 */
public class FriendDAO extends DAO {

    public FriendDAO() {
        super();
    }

    public ArrayList<User> getListFriends(Integer userId) {
        String sql = "{call get_list_friends(?)}";
        ArrayList<User> result = new ArrayList<User>();
        try {
            CallableStatement ps = con.prepareCall(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User f = new User();
                f.setId(rs.getInt("id")); //id nay la id cua tblfriend chu k phai user =)))))
                f.setName(rs.getString("name"));
                f.setUsername(rs.getString("username"));
                f.setIsOnline(rs.getInt("isOnline")); // isOnline = id cua user
                f.setAddress(rs.getString("address"));
                result.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Friend> getListRequests(Integer userId) {
        String sql = "{call get_list_requests(?)}";
        ArrayList<Friend> result = new ArrayList<Friend>();
        try {
            CallableStatement ps = con.prepareCall(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Friend f = new Friend();
                f.setId(rs.getInt("id"));
                f.setSourceName(rs.getString("name"));
                f.setSourceId(rs.getInt("sourceId"));
                f.setSourceUsername(rs.getString("username"));
                f.setStartedAt(rs.getDate("startedAt"));
                result.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteFriends(Integer friendId) {
        boolean result = true;
        String sql = "{call delete_friend(?)}";
        try {
            CallableStatement ps = con.prepareCall(sql);
            ps.setInt(1, friendId);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public boolean acceptRequest(Integer id) { //tham so truyen vao la id cua friend relationship
        String sql = "{call accept_request(?,?)}";
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
    
    public boolean addFriend(int sourceId, int targetId) { //tham so truyen vao la id cua friend relationship
        String sql = "{call add_friend(?,?,?)}";
        java.sql.Date time = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        boolean result = true;
        try {
            CallableStatement ps = con.prepareCall(sql);
            ps.setInt(1, sourceId);
            ps.setInt(2, targetId);
            ps.setDate(3, time);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public String checkFriend(int currentId, int checkId) {
        String result = "not";
        String sql = "{call check_friend(?,?)}";
        try {
            CallableStatement ps = con.prepareCall(sql);
            ps.setInt(1, currentId);
            ps.setInt(2, checkId);
            ResultSet rs = ps.executeQuery();
//            System.out.println(rs);
            while (rs.next()) {
//                Friend f = new Friend();
//                f.setId(rs.getInt("id"));
//                f.setSourceName(rs.getString("name"));
//                f.setSourceId(rs.getInt("sourceId"));
//                f.setSourceUsername(rs.getString("username"));
//                f.setStartedAt(rs.getDate("startedAt"));
//                result.add(f);
                result = rs.getString("status"); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println();
        return result;
    }
}
