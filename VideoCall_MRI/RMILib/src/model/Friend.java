/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Little Coconut
 */
public class Friend implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    private int id;
    private Date startedAt; 
    private String status;
    private int sourceId;
    private int targetId;
    private String sourceName;
    private String sourceUsername;
    private int fromId; 

    public Friend() {
    }

    public Friend(Date startedAt, String status, int sourceId, int targetId) {
        this.startedAt = startedAt;
        this.status = status;
        this.sourceId = sourceId;
        this.targetId = targetId;
    }

    public Friend(int id, Date startedAt, int sourceId, String sourceName, String sourceUsername) {
        this.id = id;
        this.startedAt = startedAt;
        this.sourceId = sourceId;
        this.sourceName = sourceName;
        this.sourceUsername = sourceUsername;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public String getSourceUsername() {
        return sourceUsername;
    }

    public void setSourceUsername(String sourceUsername) {
        this.sourceUsername = sourceUsername;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
    
    
}
