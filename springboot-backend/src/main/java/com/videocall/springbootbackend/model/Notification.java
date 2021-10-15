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
@Table(name = "tblnotification")
public class Notification {	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id; 
    
    @Column(name = "content")
    private String content;     

    @Column(name = "created_at")
    private Timestamp created_at; 
     
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;  
    
    public Notification() {
        super();
         }     
    public Notification(String content, Timestamp created_at) {
        super();
        this.content = content;
        this.created_at = created_at; 
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getCreatedAt() {
		return created_at;
	}
	public void setCreatedAt(Timestamp created_at) {
		this.created_at = created_at;
	}
}
