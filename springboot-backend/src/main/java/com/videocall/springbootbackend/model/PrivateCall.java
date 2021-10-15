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
@Table(name = "tblprivatecall")
public class PrivateCall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "ended_at")
    private Timestamp ended_at;
    
    @ManyToOne
    @JoinColumn(name = "sourceId")
    private User sourceUser;
    
    @ManyToOne
    @JoinColumn(name = "targetId")
    private User targetUser;
    
    public PrivateCall() {
        super();
    }

    public PrivateCall(Timestamp created_at, Timestamp ended_at) {
        super();
        this.created_at = created_at;
        this.ended_at = ended_at;
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

	public Timestamp getEnded_at() {
		return ended_at;
	}

	public void setEnded_at(Timestamp ended_at) {
		this.ended_at = ended_at;
	}
}
