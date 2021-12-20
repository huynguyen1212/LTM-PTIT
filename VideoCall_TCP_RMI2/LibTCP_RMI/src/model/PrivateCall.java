package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class PrivateCall implements Serializable {
    private static final long serialVersionUID = 20210811004L;
    private int id;
    private Timestamp createdAt;
    private Timestamp endedAt; 
    private User source;
    private User target;
//    private int fromId;
//    private String sourceName;
//    private String targetName;
    public PrivateCall() {
    }

    public PrivateCall(User source, User target) {
        this.source = source;
        this.target = target;
    }

    public PrivateCall(Timestamp createdAt, Timestamp endedAt, User source, User target) {
        this.createdAt = createdAt;
        this.endedAt = endedAt;
        this.source = source;
        this.target = target;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public int getFromId() {
//        return fromId;
//    }
//
//    public void setFromId(int fromId) {
//        this.fromId = fromId;
//    }

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

    public User getSource() {
        return source;
    }

    public void setSource(User source) {
        this.source = source;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

//    public String getSourceName() {
//        return sourceName;
//    }
//
//    public void setSourceName(String sourceName) {
//        this.sourceName = sourceName;
//    }
//
//    public String getTargetName() {
//        return targetName;
//    }
//
//    public void setTargetName(String targetName) {
//        this.targetName = targetName;
//    }
}
