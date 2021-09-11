package dao;

import static dao.DAO.session;
import java.util.ArrayList;
import model.Friend;
import org.hibernate.Transaction;

public class FriendDAO extends DAO {

    public FriendDAO() {
        super();
        // TODO Auto-generated constructor stub
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Friend> searchFriend(String key) {
        ArrayList<Friend> result = (ArrayList<Friend>) session.createQuery("from Customer where name like '%" + key + "%'").list();
        return result;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Friend> getAll() {
        ArrayList<Friend> result = (ArrayList<Friend>) session.createQuery("from User").list();
        return result;
    }

    /**
     * update the @friend
     *
     * @param friend
     */
    public boolean updateFriend (Friend friend) {
        Transaction ts = null;
        try {
            System.out.println(friend.toString());
            ts = session.beginTransaction();
            session.update(friend);
            ts.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean createFriend(Friend friend) {
        try {
            session.save(friend);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
