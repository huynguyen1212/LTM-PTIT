package com.videocall.springbootbackend.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tblroommember")
public class RoomMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "update_at")
    private Timestamp update_at;

    @Column(name = "nickName")
    private String nickName;
    
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "roomId", nullable = false)
    private Room room;
    
    public RoomMember() {
        super();
    }

    public RoomMember(int id, Timestamp created_at, Timestamp update_at, String nickName) {
        this.id = id;
        this.created_at = created_at;
        this.update_at = update_at;
        this.nickName = nickName;
    }

    public RoomMember(Timestamp created_at, Timestamp update_at, String nickName) {
        this.created_at = created_at;
        this.update_at = update_at;
        this.nickName = nickName;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
