package ptit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ptit.base.MyUserDetails;
import ptit.repository.FriendRepository;
import ptit.repository.UserRepository;

import java.util.List;

@Service
public class FriendService implements FriendServiceInterface{
    @Autowired
    FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public FriendEntity addFriend(UserEntity user, UserEntity friend) {
        FriendEntity checkDuplicate = friendRepository.findFirstByUser_id_1AndUser_id_2(user.getId(), friend.getId());
        if(checkDuplicate == null){
            FriendEntity friendEntity = new FriendEntity();
            friendEntity.setUser_id_1(user);
            friendEntity.setUser_id_2(friend);
            friendEntity.setConfirmed(false);
            return friendRepository.save(friendEntity);
        }
       return null;
    }

    @Override
    public FriendEntity confirmFriend(Long friend_id, Boolean status) {
        FriendEntity friendEntity = friendRepository.findById(friend_id).orElse(null);

        if(friendEntity != null){
            if(status){
                friendEntity.setConfirmed(true);
                return friendRepository.save(friendEntity);
            }else {
                friendRepository.delete(friendEntity);
            }

        }
        return null;
    }

    @Override
    public List<FriendEntity> getListFriend(String username) {
        MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<FriendEntity> friendEntityList = friendRepository.findAllByUsernameFriend(user.getUserEntity().getId(), username);
        return friendEntityList;
    }

    @Override
    public List<FriendEntity> getListRequest(String username) {
        MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<FriendEntity> friendEntityList = friendRepository.findAllByUsernameFriendRequest(user.getUserEntity().getId(), username);
        return friendEntityList;
    }
}
