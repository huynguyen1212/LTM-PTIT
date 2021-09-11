package dao;

import static dao.DAO.session;
import java.util.ArrayList;
import model.User;
import org.hibernate.Transaction;

public class UserDAO extends DAO {

    public UserDAO() {
        super();
        // TODO Auto-generated constructor stub
    }

    @SuppressWarnings("unchecked")
    public ArrayList<User> searchUser(String key) {
        ArrayList<User> result = (ArrayList<User>) session.createQuery("from Customer where name like '%" + key + "%'").list();
        return result;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<User> getAll() {
        ArrayList<User> result = (ArrayList<User>) session.createQuery("from User").list();
        return result;
    }

    /**
     * update the @user
     *
     * @param user
     */
    public boolean updateUser(User user) {
        Transaction ts = null;
        try {
            System.out.println(user.toString());
            ts = session.beginTransaction();
            session.update(user);
            ts.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean createUser(User user) {
        try {
            session.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
