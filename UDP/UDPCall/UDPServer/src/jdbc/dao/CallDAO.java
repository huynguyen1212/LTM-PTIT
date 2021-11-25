/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdbc.dao;

import dto.SendCallDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import static jdbc.dao.DAO.con;
import model.Conversation;
import model.Call;
import model.User;

/**
 *
 * @author hp
 */
public class CallDAO extends DAO {

    public ArrayList<Call> getCallConverstation(Long converstation_id) {
        ArrayList<Call> res = new ArrayList<>();
        String sql
                = "  SELECT\n"
                + "    m.id, m.time,\n"
                + "    u.id as uid,\n"
                + "    u.username as uusername ,\n"
                + "    c.id as cid,\n"
                + "    c.name as cname\n"
                + "  FROM\n"
                + "    conversation c ,\n"
                + "    calls m,\n"
                + "    user u \n"
                + "  WHERE\n"
                + "    c.id = ?\n"
                + "    and m.conversation_id = c.id\n"
                + "    and u.id = m.user_id;\n";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, converstation_id + "");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Call c = new Call();
                c.setId(rs.getLong("id"));
                c.setTime(rs.getString("time"));

                User u = new User();
                u.setId(rs.getLong("uid"));
                u.setUsername(rs.getString("uusername"));
                c.setUser(u);

                Conversation cs = new Conversation();
                cs.setId(rs.getLong("cid"));
                cs.setName(rs.getString("cname"));
                c.setConversation(cs);

                res.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean sendCall(SendCallDTO sendCallDTO) {
        String sql
                = "INSERT into calls (user_id, conversation_id, time) values (?, ?,? );";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sendCallDTO.getUser_id() + "");
            ps.setString(2, sendCallDTO.getConverstation_id() + "");
            ps.setString(3, sendCallDTO.getTime()+ "");

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
