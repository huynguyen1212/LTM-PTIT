package model;
 
import java.io.Serializable;
 
public class Friend implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    private Long id;
    private Long user_id_1;  // sent
    private Long user_id_2;  // receive
    private boolean confirmed;

    public Friend(Long user_id_1, Long user_id_2, boolean confirmed) {
        this.user_id_1 = user_id_1;
        this.user_id_2 = user_id_2;
        this.confirmed = confirmed;
    }

    public Friend() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id_1() {
        return user_id_1;
    }

    public void setUser_id_1(Long user_id_1) {
        this.user_id_1 = user_id_1;
    }

    public Long getUser_id_2() {
        return user_id_2;
    }

    public void setUser_id_2(Long user_id_2) {
        this.user_id_2 = user_id_2;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
   
    
     
    
}