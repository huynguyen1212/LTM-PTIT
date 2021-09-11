package model;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Entity
//@Indexed
@Table(name = "tblFriend")
public class Friend implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "startedAt")
    private Timestamp startedAt;

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

    public Friend(int id, Timestamp startedAt, String status) {
        this.id = id;
        this.startedAt = startedAt;
        this.status = status;
    }

    public Friend(Timestamp startedAt, String status) {
        this.startedAt = startedAt;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Timestamp startedAt) {
        this.startedAt = startedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
