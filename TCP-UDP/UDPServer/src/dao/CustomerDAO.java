/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author Little Coconut
 */
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
 
import model.Customer;
 
public class CustomerDAO extends DAO{
     
    /**
     * search all clients in the tblClient whose name contains the @key
     * using PreparedStatement - recommended for professional coding
     * @param key
     * @return list of client whose name contains the @key
     */
    public ArrayList<Customer> searchCustomer(String key){
        ArrayList<Customer> result = new ArrayList<Customer>();
        String sql = "SELECT * FROM tblclient WHERE name LIKE ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + key + "%");
            ResultSet rs = ps.executeQuery();
 
            while(rs.next()){
                Customer client = new Customer();
                client.setId(rs.getInt("id"));
                client.setName(rs.getString("name"));
                client.setIdCard(rs.getString("idcard"));
                client.setAddress(rs.getString("address"));
                client.setTel(rs.getString("tel"));
                client.setEmail(rs.getString("email"));
                client.setNote(rs.getString("note"));
                result.add(client);
            }
        }catch(Exception e){
            e.printStackTrace();
        }   
        return result;
    }
     
    /**
     * update the @client
     * @param client
     */
    public boolean editCustomer(Customer client){
        String sql = "UPDATE tblclient SET name=?, idcard =?, address=?, tel=?, email=?, note=? WHERE id=?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, client.getName());
            ps.setString(2, client.getIdCard());
            ps.setString(3, client.getAddress());
            ps.setString(4, client.getTel());
            ps.setString(5, client.getEmail());
            ps.setString(6, client.getNote());
            ps.setInt(7, client.getId());
 
            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
     
}
