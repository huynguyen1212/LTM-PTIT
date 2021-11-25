package dto;

import java.io.Serializable;

public class RequestDTO implements Serializable{
    private Long toID;
    private Long friendId;
    private String username;
    private Long fromId;

    public RequestDTO(Long toID, Long friendId, String username, Long fromId) {
        this.toID = toID;
        this.friendId = friendId;
        this.username = username;
        this.fromId = fromId;
    }
    
    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public RequestDTO() {
    }

    public Long getToID() {
        return toID;
    }

    public void setToId(Long userId) {
        this.toID = userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
}
