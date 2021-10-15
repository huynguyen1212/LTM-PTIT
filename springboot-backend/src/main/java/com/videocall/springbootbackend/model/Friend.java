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
@Table(name = "tblfriend")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "started_at")
    private Timestamp started_at;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "sourceId", nullable = false)
    private User sourceUser;
    
    @ManyToOne
    @JoinColumn(name = "targetId")
    private User targetUser;
    
    public Friend() {
        super();
    }

    public Friend(int id, Timestamp started_at, String status) {
        this.id = id;
        this.started_at = started_at;
        this.status = status;
    }

    public Friend(Timestamp started_at, String status) {
        this.started_at = started_at;
        this.status = status;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getStartedAt() {
		return started_at;
	}

	public void setStartedAt(Timestamp started_at) {
		this.started_at = started_at;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
