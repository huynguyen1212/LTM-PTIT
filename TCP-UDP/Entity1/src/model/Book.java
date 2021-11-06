package model;

import java.io.Serializable;

public class Book implements Serializable {

    private static final long serialVersionUID = 20210811004L;
    private int id;
    private String name;
    private String type;
    private String author;
    private String expressed_year;
    private String desscription;

    public Book() {
        super();
    }

    public Book(int id, String name, String type, String author, String expressed_year, String desscription) {
        this.name = name;
        this.type = type;
        this.author = author;
        this.expressed_year = expressed_year;
        this.desscription = desscription;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getExpressed_year() {
        return expressed_year;
    }

    public void setExpressed_year(String expressed_year) {
        this.expressed_year = expressed_year;
    }

    public String getDesscription() {
        return desscription;
    }

    public void setDesscription(String desscription) {
        this.desscription = desscription;
    }

}
