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
            ps.setInt(1, privateCall.getSourceId());
            ps.setInt(2, privateCall.getTargetId());  
            ResultSet rs = ps.executeQuery();
            while (rs.next()) { 
                privateCall.setId(rs.getInt("LAST_INSERT_ID()"));
                User source = ud.getUserById(privateCall.getSourceId());
                User target = ud.getUserById(privateCall.getTargetId());
                privateCall.setSourceName(source.getUsername());
                privateCall.setTargetName(target.getUsername());
            } 
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return privateCall;
    }
}
