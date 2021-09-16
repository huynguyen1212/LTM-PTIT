package dao;

import static dao.DAO.session;
import java.util.ArrayList;
import model.Notification;
import org.hibernate.Transaction;

public class NotificationDAO  extends DAO{ 
    public NotificationDAO() {
        super();
    }    
     public boolean createNotification(Notification noti){
        try{ 
            session.save(noti); 
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }        
        return true;
    }      
    public boolean deleteNotification(Notification noti){
        try{ 
            session.delete(noti); 
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }        
        return true;
    }      
}

