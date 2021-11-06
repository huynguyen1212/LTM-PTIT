/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Little Coconut
 */
public class GroupCall implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    private int id;
    private Timestamp createdAt;
    private Timestamp endedAt; 
    private int groupId;
    private int createdId;

    public GroupCall() {
    }

    public GroupCall(int id, Timestamp createdAt, Timestamp endedAt, int groupId, int createdId) {
        this.createdAt = createdAt;
        this.endedAt = endedAt;
        this.groupId = groupId;
        this.createdId = createdId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Timestamp endedAt) {
        this.endedAt = endedAt;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getCreatedId() {
        return createdId;
    }

    public void setCreatedId(int createdId) {
        this.createdId = createdId;
    }
    
    
}
