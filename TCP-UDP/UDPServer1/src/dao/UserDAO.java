package dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import static dao.DAO.con;
import model.Reader;

import model.User;

public class UserDAO extends DAO {

    public UserDAO() {
        super();
    }

    public Integer checkLogin(User user) {
        Integer result = -1;
        String sql = "SELECT  id FROM user WHERE username = ? AND password = ?";
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

    public Integer addUser(User user) {
        Integer result = -1;
        java.sql.Date createdAt = new java.sql.Date(Calendar.getInstance().getTimeInMillis());

        String sql = "INSERT INTO user (username,password,fullname,position,note) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullname());
            ps.setString(4, user.getPosition());
            ps.setString(5, user.getNote());

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = -1;
        }
        return 1;
    }
}
