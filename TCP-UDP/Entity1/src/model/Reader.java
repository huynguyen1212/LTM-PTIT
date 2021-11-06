package model;

import java.io.Serializable;

public class Reader implements Serializable {

    private static final long serialVersionUID = 20210811004L;
    private int id;
    private String name;
    private String address;
    private String tel;
    private String email;
    private String note;

    public Reader() {
        super();
    }

    public Reader(String name, String address, String tel, String email, String note) {
        super();
        this.name = name;
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
}
