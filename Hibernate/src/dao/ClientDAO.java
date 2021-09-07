package dao;

import java.util.ArrayList;
import model.Client;
import org.hibernate.Transaction;

public class ClientDAO extends DAO {
    
    public ClientDAO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ArrayList<Client> searchClient(String key) {
        ArrayList<Client> result = new ArrayList<Client>();
        try {
            result = (ArrayList<Client>) session.createQuery("from Client where name like '%" + key + "%'").list();
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
    public boolean addClient(Client client) {
        try {
            session.save(client);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateClient(Client client) {
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

    public boolean deleteClient(int id) {
        Transaction ts = null;
        try {
            Client client = (Client) session.get(Client.class, id);
            ts = session.beginTransaction();
            session.delete(client);
            ts.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
