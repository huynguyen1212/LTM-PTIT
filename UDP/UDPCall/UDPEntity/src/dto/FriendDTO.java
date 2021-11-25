package dto;

import java.io.Serializable;

public class FriendDTO implements Serializable{
    private Long userId;
    private String username;
    private Long friendIdUser1;
    private Long friendIdUser2;
    private Long friendId;
    private int confirmed;

    public FriendDTO() {
    }

    public FriendDTO(Long userId, String username, Long friendIdUser1, Long friendIdUser2, Long friendId, int confirmed) {
        this.userId = userId;
        this.username = username;
        this.friendIdUser1 = friendIdUser1;
        this.friendIdUser2 = friendIdUser2;
        this.friendId = friendId;
        this.confirmed = confirmed;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getFriendIdUser1() {
        return friendIdUser1;
    }

    public void setFriendIdUser1(Long friendIdUser1) {
        this.friendIdUser1 = friendIdUser1;
    }

    public Long getFriendIdUser2() {
        return friendIdUser2;
    }

    public void setFriendIdUser2(Long friendIdUser2) {
        this.friendIdUser2 = friendIdUser2;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public String toString() {
        return "FriendDTO{" + "userId=" + userId + ", username=" + username + ", friendIdUser1=" + friendIdUser1 + ", friendIdUser2=" + friendIdUser2 + ", friendId=" + friendId + ", confirmed=" + confirmed + '}';
    }
    
    
}
