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

public class GroupMemberDAO extends DAO {

    public GroupMemberDAO() {
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
        System.out.println(result);

        return result;
    }

    public boolean leaveGroup(int group, int userId) {
        boolean result = true;
        String sql = "{call delete_group_member(?,?)}";
        try {
            CallableStatement ps = con.prepareCall(sql);
            ps.setInt(1, group);
            ps.setInt(2, userId);
            ps.execute();
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
}
