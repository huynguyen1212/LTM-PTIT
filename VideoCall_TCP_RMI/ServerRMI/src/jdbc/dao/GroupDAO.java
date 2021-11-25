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

public class GroupDAO extends DAO {

    public GroupDAO() {
        super();
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
}
