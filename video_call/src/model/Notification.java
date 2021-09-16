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
@Table(name = "tblNotification")
public class Notification implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id; 
    
    @Column(name = "content")
    private String content;     

    @Column(name = "createdAt")
    private String createdAt; 
     
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;   
     
    public Notification() {
        super();
         }     
    public Notification(String content, String createdAt) {
        super();
        this.content = content;
        this.createdAt = createdAt; 
    }
 }
