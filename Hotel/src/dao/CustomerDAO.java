package dao;

import dao.DAO;
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
        try {
            // System.out.println(client.getAddress());
            Transaction tx = session.beginTransaction();
            DAO.session.update(client);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addCustomer(Customer client) {
        DAO.session.save(client);
        return true;
    }

    public boolean deleteCustomer(Customer client) {
        //System.out.println(client.toString());
        try {
            Transaction tx = session.beginTransaction();
            DAO.session.delete(client);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
