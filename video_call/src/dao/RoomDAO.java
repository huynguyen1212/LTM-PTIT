package dao;

import static dao.DAO.session;
import java.util.ArrayList;
import model.Room;
import org.hibernate.Transaction;

public class RoomDAO extends DAO{
    public RoomDAO() {
        super();
    }
 
    @SuppressWarnings("unchecked")
    public ArrayList<Room> searchRoomByName(String key){
        ArrayList<Room> result = (ArrayList<Room>)session.createQuery("from Customer where name like '%"+key+"%'").list();
        return result;
    }
     
    public boolean updateRoom(Room room){
        try{ 
            session.update(room); 
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }        
        return true;
    }       
    
    public boolean deleteRoom(Room room){
        try{ 
            session.delete(room); 
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        
        return true;
    }   
    
     public boolean createRoom(Room room){
        try{ 
            session.save(room); 
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        
        return true;
    } 
}
