package model;

import java.io.Serializable;
import java.sql.Date;

public class Friend implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    private int id;
    private Date startedAt; 
    private String status;
    private User source;
    private User target;
//    private String sourceName;
//    private String sourceUsername;
//    private int fromId; 

    public Friend() {
    }

    public Friend(Date startedAt, String status, User source, User target) {
        this.startedAt = startedAt;
        this.status = status;
        this.source = source;
        this.target = target;
    }

    public Friend(int id, Date startedAt, User source, String sourceName, String sourceUsername) {
        this.id = id;
        this.startedAt = startedAt;
        this.source = source;
//        this.sourceName = sourceName;
//        this.sourceUsername = sourceUsername;
    }

//    public int getFromId() {
//        return fromId;
//    }
//
//    public void setFromId(int fromId) {
//        this.fromId = fromId;
//    }
//
//    public String getSourceUsername() {
//        return sourceUsername;
//    }
//
//    public void setSourceUsername(String sourceUsername) {
//        this.sourceUsername = sourceUsername;
//    }
//
//    public String getSourceName() {
//        return sourceName;
//    }
//
//    public void setSourceName(String sourceName) {
//        this.sourceName = sourceName;
//    }
    
    

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
    
    
}
