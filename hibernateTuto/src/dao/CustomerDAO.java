package dao;

import java.util.ArrayList;
import model.Customer;
import org.hibernate.Transaction;

public class CustomerDAO extends DAO {

    public CustomerDAO() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * search all clients in the tblClient whose name contains the @key
     *
     * @param key
     * @return list of client whose name contains the @key
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Customer> searchCustomer(String key) {
        ArrayList<Customer> result = (ArrayList<Customer>) session.createQuery("from Customer where name like '%" + key + "%'").list();
        return result;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Customer> getAll() {
        ArrayList<Customer> result = (ArrayList<Customer>) session.createQuery("from Customer").list();
        return result;
    }

    /**
     * update the @client
     *
     * @param client
     */
    public boolean editCustomer(Customer client) {
        Transaction ts = null;
        try {
            System.out.println(client.toString());
            ts = session.beginTransaction();
            session.update(client);
            ts.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteCustomer(int id) {
        Transaction ts = null;
        try {
            Customer client = (Customer) session.get(Customer.class, id);
            ts = session.beginTransaction();
            session.delete(client);
            ts.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addCustomer(Customer customer) {
        try {
            session.save(customer);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
