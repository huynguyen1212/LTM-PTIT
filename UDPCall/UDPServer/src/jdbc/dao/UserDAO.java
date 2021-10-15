package jdbc.dao;

import dto.FriendDTO;
import dto.RequestDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import static jdbc.dao.DAO.con;

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

    public ArrayList<FriendDTO> getUsers(User user) {
        ArrayList<FriendDTO> users = new ArrayList<>();
//        String sql = "select DISTINCT user.username, user.id from user where user.username like ? and not user.id=? and user.id not in (select distinct user.id from user, friend where friend.user_id_1 = user.id or friend.user_id_2 = user.id and friend.user_id_1=?)";

        String sql = "select user.id, user.username, friend.id as friendId, friend.user_id_1, friend.user_id_2, friend.confirmed from (SELECT DISTINCT user.id, user.username from user, friend where NOT user.id = friend.user_id_1 AND NOT user.id = friend.user_id_2 AND NOT user.id = ?) AS user LEFT JOIN (select * from friend where friend.user_id_1 = ? or friend.user_id_2 = ?) AS friend ON (user.id = friend.user_id_1 OR user.id = friend.user_id_2) where user.username like ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setLong(1, user.getId());
            ps.setLong(2, user.getId());
            ps.setLong(3, user.getId());
            ps.setString(4, '%' + user.getUsername() + '%');

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Long userId = rs.getLong("user.id");
                String username = rs.getString("user.username");
                Long friendUserId1 = rs.getObject("friend.user_id_1") == null ? null : rs.getLong("friend.user_id_1");
                Long friendUserId2 = rs.getObject("friend.user_id_2") == null ? null : rs.getLong("friend.user_id_2");
                Long friendId = rs.getObject("friendId") == null ? null : rs.getLong("friendId");
                int friendConfirmed = rs.getInt("friend.confirmed");
                
//                if(rs.wasNull()) {
//                    System.out.println("NULL");
//                }

                users.add(new FriendDTO(userId, username, friendUserId1, friendUserId2, friendId, friendConfirmed));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean triggerStatus(User user) {
        boolean res = true;
        String sql = "update user set online=? where id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getOnline());
            ps.setLong(2, user.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            res = false;
            e.printStackTrace();
        }
        return res;
    }

}
