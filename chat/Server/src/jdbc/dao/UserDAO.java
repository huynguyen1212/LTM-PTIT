package jdbc.dao;

import dto.RequestDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import model.User;

public class UserDAO extends DAO {

    public UserDAO() {
        super();
    }

    public User login(User user) {
        User res = null;
        String sql = "SELECT DISTINCT id, username FROM user WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                res = new User();
                res.setId(rs.getLong("id"));
                res.setUsername(rs.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean signup(User user) {
        boolean result = true;
        String sql = "INSERT INTO user (username, password) VALUES(?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public ArrayList<User> getUsers(User user) {
        ArrayList<User> users = new ArrayList<>();
        String sql = "select DISTINCT user.username, user.id from user where user.username like ? and not user.id=? and user.id not in (select user.id from user, friend where friend.user_id_1 = user.id or friend.user_id_2 = user.id and friend.user_id_1=?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, '%' + user.getUsername() + '%');
            ps.setLong(2, user.getId());
            ps.setLong(3, user.getId());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getString("username"), rs.getLong("id")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean checkHasAddedFriend(ArrayList<Long> ids) {
        boolean res = false;
        String sql = "select DISTINCT friend.id from friend where friend.user_id_1=? and friend.user_id_2=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, ids.get(0));
            ps.setLong(2, ids.get(1));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                res = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean addFriend(ArrayList<Long> ids) {
        boolean res = true;
        if (checkHasAddedFriend(ids)) {
            return res;
        }
        String sql = "insert into friend (user_id_1, user_id_2, confirmed) values (?, ?, false)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, ids.get(0));
            ps.setLong(2, ids.get(1));

            ps.executeUpdate();

        } catch (Exception e) {
            res = false;
            e.printStackTrace();
        }
        return res;
    }

    public ArrayList<RequestDTO> getRequests(Long id) {
        ArrayList<RequestDTO> res = new ArrayList<>();
        String sql = "select user.username, user.id as user_id, friend.id as friend_id from user inner join friend on user.id=friend.user_id_1 and friend.user_id_2=? and friend.confirmed=0";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                res.add(new RequestDTO(rs.getLong("user_id"), rs.getLong("friend_id"), rs.getString("username")));
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return res;
    }
    
    public boolean confirmFriend (Long friendId) {
        boolean res = true;
        String sql = "UPDATE friend SET confirmed='1' WHERE (id=?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, friendId);
            ps.executeUpdate();
        }
        catch (Exception e) {
            res = false;
            e.printStackTrace();
        }
        return res;
    }
}
