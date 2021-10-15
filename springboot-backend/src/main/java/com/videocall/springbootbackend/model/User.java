package com.videocall.springbootbackend.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="tbluser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable=false)
    private int id;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "update_at")
    private Timestamp update_at;

    @Column(name = "address")
    private String address;

    @Column(name = "username", nullable=false)
    private String username;

    @Column(name = "password", nullable=false)
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "last_online")
    private Timestamp last_online;

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
    
    public User(String username, String password1) {
        this.username = username;
        this.password = password1;
    }
    
    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", created_at=" + created_at +
                ", updated_at=" + update_at +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", last_online=" + last_online +
                ", online=" + online +
                '}';
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public Timestamp getUpdate_at() {
		return update_at;
	}

	public void setUpdate_at(Timestamp update_at) {
		this.update_at = update_at;
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

	public Timestamp getLast_online() {
		return last_online;
	}

	public void setLast_online(Timestamp last_online) {
		this.last_online = last_online;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}
    
    
}
