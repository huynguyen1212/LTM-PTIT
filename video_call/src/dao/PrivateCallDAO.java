package dao;

import static dao.DAO.session;
import java.util.ArrayList;
import model.PrivateCall;
import org.hibernate.Transaction;

public class PrivateCallDAO  extends DAO{ 
    public PrivateCallDAO() {
        super();
    }    
    
     public boolean createPrivateCall(PrivateCall call){
        try{ 
            session.save(call); 
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        
        return true;
    } 
    
    public boolean deletePrivateCall(PrivateCall call){
        try{ 
            session.delete(call); 
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        
        return true;
    }   
}
