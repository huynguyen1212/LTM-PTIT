/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Little Coconut
 */
public class SearchWrapper  implements Serializable{
    private String key;
    private int fromId;
    
    public SearchWrapper() {
    }

    public SearchWrapper(String key, int fromId) {
        this.key = key;
        this.fromId = fromId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }
    
    
}
