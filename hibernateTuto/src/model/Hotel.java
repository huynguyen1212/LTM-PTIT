package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Entity
//@Indexed
@Table(name = "hotel")
public class Hotel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "startLevel")
    private int startLevel;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy="hotelID")
    private List<Room> listRoom;

    public Hotel() {
        super();
    }

    public Hotel(int id, String name, String address, int startLevel, String description) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.startLevel = startLevel;
        this.description = description;
    }

    public Hotel(String name, String address, int startLevel, String description) {
        super();
        this.name = name;
        this.address = address;
        this.startLevel = startLevel;
        this.description = description;
    }

}
