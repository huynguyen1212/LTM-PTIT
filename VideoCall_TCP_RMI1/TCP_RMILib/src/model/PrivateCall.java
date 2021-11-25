/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Little Coconut
 */
public class PrivateCall implements Serializable {
    private static final long serialVersionUID = 20210811004L;
    private int id;
    private Timestamp createdAt;
    private Timestamp endedAt; 
    private int sourceId;
    private int targetId;
    private int fromId;
    private String sourceName;
    private String targetName;
    public PrivateCall() {
    }

    public PrivateCall(int sourceId, int targetId) {
        this.sourceId = sourceId;
        this.targetId = targetId;
    }

    public PrivateCall(Timestamp createdAt, Timestamp endedAt, int sourceId, int targetId) {
        this.createdAt = createdAt;
        this.endedAt = endedAt;
        this.sourceId = sourceId;
        this.targetId = targetId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
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

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }
    
    
}
