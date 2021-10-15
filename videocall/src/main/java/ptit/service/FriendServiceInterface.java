package ptit.service;

import java.util.List;

public interface FriendServiceInterface {
    FriendEntity addFriend(UserEntity user, UserEntity friend);
    FriendEntity confirmFriend(Long friend_id, Boolean status);
    List<FriendEntity> getListFriend(String username);
    List<FriendEntity> getListRequest(String username);
}
