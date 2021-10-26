package jdbc.dao;
 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
 
import model.User;
 
public class UserDAO extends DAO{
     
    public UserDAO() {
        super();
    }
     
    public boolean checkLogin(User user) {
        boolean result = false;
        String sql = "SELECT name, position FROM tblUser WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
             
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                user.setUserName(rs.getString("name"));
                result = true;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}