package jdbc.dao;

import static jdbc.dao.DAO.con;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import model.PrivateCall;
import model.User;

public class PrivateCallDAO extends DAO{

    public PrivateCallDAO() {
        super();
    }

    public PrivateCall createPrivateCall(PrivateCall privateCall) { 
        java.sql.Timestamp createdAt = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()); 
        String sql = "{call create_private_call(?,?,?)}";
        UserDAO ud = new UserDAO();

        try {
            CallableStatement ps = con.prepareCall(sql);
            ps.setTimestamp(3, createdAt);
            ps.setInt(1, privateCall.getSource().getId());
            ps.setInt(2, privateCall.getTarget().getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) { 
                privateCall.setId(rs.getInt("LAST_INSERT_ID()"));
                User source = ud.getUserById(privateCall.getSource().getId());
                User target = ud.getUserById(privateCall.getTarget().getId());
                privateCall.setSource(source);
                privateCall.setTarget(target);
            } 
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return privateCall;
    }
}
