/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.DAO.con;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Group;
import model.GroupMember;
import model.User;

/**
 *
 * @author Little Coconut
 */
public class GroupDAO extends DAO {

    public GroupDAO() {
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
        return result;
    }

    public boolean leaveGroup(GroupMember member) {
        boolean result = true;
        String sql = "{call delete_group_member(?,?)}";
        try {
            CallableStatement ps = con.prepareCall(sql);
            ps.setInt(1, member.getUserId());
            ps.setInt(2, member.getGroupId()); 
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            result=false;
        }
        return result;
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
            result=false;
        }
        return result;
    }
}
