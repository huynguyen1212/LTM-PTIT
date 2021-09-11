package dao;

import static dao.DAO.session;
import java.util.ArrayList;
import model.RoomCall;
import org.hibernate.Transaction;

public class RoomCallDAO extends DAO {

    public RoomCallDAO() {
        super();
        // TODO Auto-generated constructor stub
    }

    @SuppressWarnings("unchecked")
    public ArrayList<RoomCall> getAll() {
        ArrayList<RoomCall> result = (ArrayList<RoomCall>) session.createQuery("from User").list();
        return result;
    }

    /**
     * update the @roomcall
     *
     * @param roomcall
     */

    public boolean deleteRoomMember(int id) {
        Transaction ts = null;
        try {
            RoomCall roomcall = (RoomCall) session.get(RoomCall.class, id);
            ts = session.beginTransaction();
            session.delete(roomcall);
            ts.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean createRoomMember(RoomCall roomcall) {
        try {
            session.save(roomcall);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
