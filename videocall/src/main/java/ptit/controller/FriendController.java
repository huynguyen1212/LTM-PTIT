package ptit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ptit.base.BaseResponse;
import ptit.base.MyUserDetails;
import ptit.dto.FriendConfirmDto;
import ptit.dto.FriendListDto;
import ptit.dto.UserAddFriendDto;
import ptit.service.FriendService;
import ptit.service.UserService;

import java.util.*;

@RestController
@RequestMapping("/api")
public class FriendController {
    @Autowired
    UserService userService;

    @Autowired
    FriendService friendService;

    @PostMapping("/add-friend")
    public BaseResponse<Object> addFriend(@RequestBody UserAddFriendDto userAddFriendDto){
        MyUserDetails currentUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity friendUser = userService.findOne(userAddFriendDto.getFriend_id());

        if(friendUser == null && friendUser.getId() == currentUser.getUserEntity().getId()){
            return BaseResponse.builder().code("400").message("Friend Id không hợp lệ.").build();
        }

        // add friend
        FriendEntity friendEntity = friendService.addFriend(currentUser.getUserEntity(), friendUser);
        if(friendEntity != null){
            return BaseResponse.builder().code("200").message("Đã gửi lời mời kết bạn.").body(friendEntity).build();
        }
        return BaseResponse.builder().code("400").message("Friend Id không hợp lệ.").build();
    }

    @PostMapping("/confirm-friend")
    public BaseResponse<Object> confirmFriend(@RequestBody FriendConfirmDto friendConfirmDto){
        FriendEntity friendEntity = friendService.confirmFriend(friendConfirmDto.getFriend_id(), friendConfirmDto.getStatus());

        if(friendEntity != null){
            return BaseResponse.builder().code("200").message("Kết bạn thành công.").body(friendEntity).build();
        }

        if(!friendConfirmDto.getStatus()){
            return BaseResponse.builder().code("200").message("Từ chối thành công.").body(null).build();
        }

        return BaseResponse.builder().code("400").message("Không thành công.").body(null).build();
    }

    // get list friend of current user
    @GetMapping("/list-friend")
    public BaseResponse<Object> getListUser(@RequestParam(required = false, defaultValue = "") String username){
        List<FriendEntity> friendEntityList = friendService.getListFriend(username);
        MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ArrayList<FriendListDto> res = new ArrayList<>();

        friendEntityList.forEach(friendEntity -> {
            FriendListDto friendListDto = new FriendListDto();

            friendListDto.setId(friendEntity.getId());
            friendListDto.setBlocking_id(friendEntity.getBlocking_id());
            friendListDto.setConfirmed(friendEntity.getConfirmed());
            friendListDto.setCreated_at(friendEntity.getCreated_at());
            friendListDto.setUpdated_at(friendEntity.getUpdated_at());

            if(user.getUserEntity().getId() == friendEntity.getUser_id_1().getId()){
                friendListDto.setFriend(friendEntity.getUser_id_2());
            }else {
                friendListDto.setFriend(friendEntity.getUser_id_1());
            }
            res.add(friendListDto);
        });

        return BaseResponse.builder().code("200").message("Thành công.").body(res).build();
    }

    // get list user request
    @GetMapping("/list-request")
    public BaseResponse<Object> getListRequest(@RequestParam(required = false, defaultValue = "") String username){
        List<FriendEntity> friendEntityList = friendService.getListRequest(username);
        return BaseResponse.builder().code("200").message("Thành công.").body(friendEntityList).build();
    }
}
