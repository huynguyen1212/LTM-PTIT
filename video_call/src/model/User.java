package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Entity
//@Indexed
@Table(name = "tblUser")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "createdAt")
    private Timestamp createdAt;

    @Column(name = "updateAt")
    private Timestamp updateAt;

    @Column(name = "address")
    private String address;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "lastOnline")
    private Timestamp lastOnline;

    @Column(name = "online")
    private int online;

    @OneToMany(mappedBy = "sourceUser")
    private List<Friend> listSourceFriend;

    @OneToMany(mappedBy = "targetUser")
    private List<Friend> listTargetFriend;

    @OneToMany(mappedBy = "user")
    private List<Room> listRoom;

    @OneToMany(mappedBy = "user")
    private List<RoomMember> listRoomMember;

    @OneToMany(mappedBy = "user")
    private List<RoomCall> listRoomCall;

    @OneToMany(mappedBy = "user")
    private List<Notification> listNotification;

    @OneToMany(mappedBy = "sourceUser")
    private List<Friend> listMadeCall;

    @OneToMany(mappedBy = "targetUser")
    private List<Friend> listReceivedCall;

    public User() {
        super();
    }

    public User(int id, Timestamp createdAt, Timestamp updateAt, String address, String username,
            String password, String avatar, Timestamp lastOnline, int online) {
        this.id = id;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.address = address;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.lastOnline = lastOnline;
        this.online = online;
    }

    public User(Timestamp createdAt, Timestamp updateAt, String address, String username,
            String password, String avatar, Timestamp lastOnline, int online) {
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.address = address;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.lastOnline = lastOnline;
        this.online = online;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreateAt() {
        return createdAt;
    }

    public void setCreateAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }
}
