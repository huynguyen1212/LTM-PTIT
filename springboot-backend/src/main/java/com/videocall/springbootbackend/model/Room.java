package com.videocall.springbootbackend.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tblroom")

public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;         
         @Column(name = "name")
    private String name;   
  
    @Column(name = "image")
    private String image;    
 
    @Column(name = "created_at")
    private Timestamp created_at;    
 
    @Column(name = "updated_at")
    private Timestamp updated_at;
    
    @ManyToOne
    @JoinColumn(name = "createdBy")
    private User user;  
  
    @OneToMany(mappedBy = "room")
    private List<RoomCall> listRoomCall; 
    
    @OneToMany(mappedBy = "room")
    private List<RoomMember> listRoomMember;
    
    public Room() {
        super();
    }

    public Room(String name, String image, Timestamp created_at, Timestamp updated_at) {
        super();
        this.name = name;
        this.image = image;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public Timestamp getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
}
