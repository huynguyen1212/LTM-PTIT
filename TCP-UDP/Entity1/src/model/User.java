package model;

import static com.oracle.jrockit.jfr.ContentType.Timestamp;
import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Date;

public class User implements Serializable {

    private static final long serialVersionUID = 20210811010L;
    private int id;
    private String username;
    private String password;
    private String fullname;
    private String position;
    private String note;

    public User() {
        super();
    }

    //constructor for Signup

    public User(String username, String password, String fullname, String position, String note) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.position = position;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
