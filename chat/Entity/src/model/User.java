package model;
import java.io.Serializable;
 
public class User implements Serializable{
    private static final long serialVersionUID = 20210811010L;
    private Long id;
    private String username;
    private String password;
     
    public User() {
        super();
    }
 
    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }
    
    public User(String username, Long id) {
        super();
        this.username = username;
        this.id = id;
    }
 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + '}';
    }

 
}