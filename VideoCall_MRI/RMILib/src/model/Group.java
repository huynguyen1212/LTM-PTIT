/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Little Coconut
 */
public class Group implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    private int id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private String image;
    private int createdBy;
    private int fromId;

    public Group() {
    }

    public Group(Date createdAt, Date updatedAt, String name, String image, int createdBy) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.image = image;
        this.createdBy = createdBy;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
    
    
}
