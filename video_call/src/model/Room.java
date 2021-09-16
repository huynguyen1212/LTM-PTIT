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
import javax.persistence.OneToMany;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Entity
@Table(name = "tblRoom")
public class Room implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;         
         @Column(name = "name")
    private String name;   
  
    @Column(name = "image")
    private String image;    
 
    @Column(name = "createdAt")
    private String createdAt;    
 
    @Column(name = "updatedAt")
    private String updatedAt;  
 
    @ManyToOne
    @JoinColumn(name = "createdBy")
    private User user;  
  
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    List<modelvideocall.RoomCall> listRoomCall; 
    
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    List<modelvideocall.RoomMember> listRoomMember;
    
    public Room() {
        super();
    }
     
         public Room(String name, String image, String createdAt, String  updatedAt) {
        super();
        this.name = name;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt; 
    }
}

