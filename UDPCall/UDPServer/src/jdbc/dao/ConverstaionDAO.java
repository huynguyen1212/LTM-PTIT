/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdbc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import static jdbc.dao.DAO.con;
import model.Conversation;
import model.User;
import utils.LoggableStatement;

/**
 *
 * @author hp
 */
public class ConverstaionDAO extends DAO {

    public boolean createConverstation(String name) {

        return false;
    }

    public ArrayList<User> getAllUserInConverstation(Long cid) {
        ArrayList<User> res = new ArrayList<>();
        String sql = "	SELECT\n"
                + "	u.*\n"
                + "	from\n"
                + "		conversation_user cu ,\n"
                + "		user u\n"
                + "	WHERE\n"
                + "		cu.conversation_id = ?\n"
                + "		and cu.user_id = u.id;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cid + "");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User c = new User();
                c.setId(rs.getLong("id"));
                c.setUsername(rs.getString("username"));
                c.setOnline(rs.getInt("online"));
                res.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    // get all converstation of an user
    public ArrayList<Conversation> getAllConverstation(Long user_id) {
        ArrayList<Conversation> res = new ArrayList<>();
        String sql = "SELECT c.id , c.name from conversation_user cu, conversation c WHERE cu.user_id = ? and cu.conversation_id = c.id;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(user_id));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Conversation c = new Conversation();
                c.setId(rs.getLong("id"));
                c.setName(rs.getString("name"));
                // get list User
                ArrayList<User> listUser = getAllUserInConverstation(c.getId());
                c.setUsers(listUser);
                res.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
