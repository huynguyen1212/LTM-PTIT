/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author sonht
 */

@Entity
@Table(name = "customer")
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "idcard")
    private String idCard;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "tel")
    private String tel;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "note")
    private String note;

    public Client() {
    }

    public Client(int id, String name, String idCard, String address, String tel, String email, String note) {
        this.id = id;
        this.name = name;
        this.idCard = idCard;
        this.address = address;
        this.tel = tel;
        this.email = email;
        this.note = note;
    }
    
    public Client(String name, String idCard, String address, String tel, String email, String note) {
        this.name = name;
        this.idCard = idCard;
        this.address = address;
        this.tel = tel;
        this.email = email;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name=" + name + ", idCard=" + idCard + ", address=" + address + ", tel=" + tel + ", email=" + email + ", note=" + note + '}';
    }
    
}
