package model;
import static com.oracle.jrockit.jfr.ContentType.Timestamp;
import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Date;
 
public class User implements Serializable{
    private static final long serialVersionUID = 20210811010L;
    private int id;
    private Date createdAt;
    private Date updatedAt;
    private String address;
    private String name;
    private String username;
    private String password;
    private String avatar;
    private Timestamp lastOnline;
    private int isOnline;
    private int isOnCall;
    private int fromId;

     
    public User() {
        super();
    }
    
    //constructor for Signup

    public User(Date createdAt, Date updatedAt, String address, String name, String username, String password) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.address = address;
        this.name = name;
        this.username = username;
        this.password = password;
    }
    
    
    public User(Date createdAt, Date updatedAt, String address, String name, String username, String password, String avatar, Timestamp lastOnline, int isOnline, int isOnCall) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.address = address;
        this.name = name;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.lastOnline = lastOnline;
        this.isOnline = isOnline;
        this.isOnCall = isOnCall;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Timestamp getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(Timestamp lastOnline) {
        this.lastOnline = lastOnline;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    public int getIsOnCall() {
        return isOnCall;
    }

    public void setIsOnCall(int isOnCall) {
        this.isOnCall = isOnCall;
    }

    
}