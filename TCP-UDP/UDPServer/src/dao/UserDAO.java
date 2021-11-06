package dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import static dao.DAO.con;
import model.Customer;

import model.User;

public class UserDAO extends DAO {

    public UserDAO() {
        super();
    }

    public Integer checkLogin(User user) {
        Integer result = -1;
        String sql = "SELECT  id FROM tbluser WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                result = user.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean addUser(User user) {
        boolean result = true;
        java.sql.Date createdAt = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
//        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        String sql = "{call user_add(?,?,?,?,?)}";
        try {
            CallableStatement ps = con.prepareCall(sql);
            ps.setDate(1, createdAt);
            ps.setString(2, user.getAddress());
            ps.setString(3, user.getName());
            ps.setString(4, user.getUsername());
            ps.setString(5, user.getPassword());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public void editIsOnline(int userId, int status) {
        String sql = "{call edit_online(?,?)}";
        try {
            CallableStatement ps = con.prepareCall(sql);
            ps.setInt(1, userId);
            ps.setInt(2, status);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> searchUser(String key, int sourceId) { //search by name and username
        ArrayList<User> result = new ArrayList<User>();
        String sql = "SELECT * FROM tbluser WHERE username LIKE ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + key + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (sourceId != rs.getInt("id")) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setIsOnline(rs.getInt("isOnline"));
                    user.setName(rs.getString("name"));
                    user.setUsername(rs.getString("username"));
                    user.setAddress(rs.getString("address"));
                    result.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return result;
    }
}
