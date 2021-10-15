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
@Table(name = "tblPrivateCall")
public class PrivateCall implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "endedAt")
    private String endedAt;

    @ManyToOne
    @JoinColumn(name = "sourceId")
    private User sourceUser;

    @ManyToOne
    @JoinColumn(name = "targetId")
    private User targetUser;

    public PrivateCall() {
        super();
    }

    public PrivateCall(String createdAt, String endedAt) {
        super();
        this.createdAt = createdAt;
        this.endedAt = endedAt;
    }
}
