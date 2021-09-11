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
@Table(name = "tblRoomCall")
public class RoomCall implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "createdAt")
    private Timestamp createdAt;

    @Column(name = "endedAt")
    private Timestamp endedAt;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "roomId", nullable = false)
    private Room room;

    public RoomCall() {
        super();
    }

    public RoomCall(int id, Timestamp createdAt, Timestamp endedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.endedAt = endedAt;
    }

    public RoomCall(Timestamp createdAt, Timestamp endedAt) {
        this.createdAt = createdAt;
        this.endedAt = endedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreateAt() {
        return createdAt;
    }

    public void setCreateAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Timestamp endedAt) {
        this.endedAt = endedAt;
    }
}
