package model;

import java.io.Serializable;

public class LoanSlip implements Serializable {

    private static final long serialVersionUID = 20210811004L;
    private int id;
    private int id_user;
    private int id_reader;
    private int id_book;

    public LoanSlip() {
        super();
    }

    public LoanSlip(int id, int id_user, int id_reader, int id_book) {
        this.id = id;
        this.id_user = id_user;
        this.id_reader = id_reader;
        this.id_book = id_book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_reader() {
        return id_reader;
    }

    public void setId_reader(int id_reader) {
        this.id_reader = id_reader;
    }

    public int getId_book() {
        return id_book;
    }

    public void setId_book(int id_book) {
        this.id_book = id_book;
    }


}
