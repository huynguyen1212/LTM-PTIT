package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Customer;

public class CustomerDAO extends DAO {

    /**
     * search all clients in the tblClient whose name contains the @key
     *
     * @param key
     * @return list of client whose name contains the @key
     */
    public ArrayList<Customer> searchCustomer(String key) {
        ArrayList<Customer> result = new ArrayList<Customer>();
        String sql = "SELECT * FROM customer WHERE name LIKE ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + key + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setIdCard(rs.getString("idcard"));
                customer.setAddress(rs.getString("address"));
                customer.setTel(rs.getString("tel"));
                customer.setEmail(rs.getString("email"));
                customer.setNote(rs.getString("note"));
                result.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * add a new @client into the DB
     *
     * @param client
     */
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customer (name, idcard, address, tel, email, note) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getIdCard());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getTel());
            ps.setString(5, customer.getEmail());
            ps.setString(6, customer.getNote());

            ps.executeUpdate();

            //get id of the new inserted client
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                customer.setId(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customer SET name=?, idCard=?, address=?, tel=?, email=?, note=? WHERE id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getIdCard());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getTel());
            ps.setString(5, customer.getEmail());
            ps.setString(6, customer.getNote());
            ps.setInt(7, customer.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean deleteCustomer(int id) {
        String sql = "DELETE from customer WHERE id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
