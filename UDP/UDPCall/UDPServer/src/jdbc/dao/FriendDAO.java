package jdbc.dao;

import dto.RequestDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import static jdbc.dao.DAO.con;
import model.User;

public class FriendDAO extends DAO {

    public ArrayList<User> getFriends(Long id) {
        ArrayList<User> res = new ArrayList<>();
        String sql = "select distinct * from user where not user.id=? and user.id in (select distinct user.id from user, friend where (user.id = friend.user_id_1 or user.id = friend.user_id_2) and friend.confirmed=1 and (friend.user_id_1=? or friend.user_id_2=?))";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ps.setLong(2, id);
            ps.setLong(3, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                res.add(new User(rs.getLong("id"), rs.getString("username"), rs.getInt("online")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean checkHasAddedFriend(ArrayList<Long> ids) {
        boolean res = false;
        String sql = "select DISTINCT friend.id from friend where (friend.user_id_1=? and friend.user_id_2=?) or (friend.user_id_2=? and friend.user_id_1=?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, ids.get(0));
            ps.setLong(2, ids.get(1));
            ps.setLong(3, ids.get(0));
            ps.setLong(4, ids.get(1));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                res = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public Long addFriend(ArrayList<Long> ids) {
        Long userId2 = ids.get(1);
        if (checkHasAddedFriend(ids)) {
            return null;
        }
        String sql = "insert into friend (user_id_1, user_id_2, confirmed) values (?, ?, false)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, ids.get(0));
            ps.setLong(2, ids.get(1));

            ps.executeUpdate();

        } catch (Exception e) {
            userId2 = null;
            e.printStackTrace();
        }
        return userId2;
    }
    
    public boolean cancelAddFriend(Long friendId) {
        boolean res = true;
        String sql = "delete from friend where id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, friendId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }

    public ArrayList<RequestDTO> getRequests(Long id) {
        ArrayList<RequestDTO> res = new ArrayList<>();
        String sql = "select distinct user.username, friend.user_id_1 as from_id, friend.user_id_2 as to_id, friend.id as friend_id from user inner join friend on user.id=friend.user_id_1 and friend.user_id_2=? and friend.confirmed=0";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                res.add(new RequestDTO(rs.getLong("to_id"), rs.getLong("friend_id"), rs.getString("username"), rs.getLong("from_id")));
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return res;
    }

    public RequestDTO confirmFriend(RequestDTO requestDTO) {
        String sql = "UPDATE friend SET confirmed=1 WHERE id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, requestDTO.getFriendId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return requestDTO;
    }

    public boolean declineFriend(Long friendId) {
        boolean res = true;
        String sql = "delete from friend where id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, friendId);
            ps.executeUpdate();
        } catch (Exception e) {
            res = false;
            e.printStackTrace();
        }
        return res;
    }
}
