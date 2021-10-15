package ptit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ptit.base.BaseResponse;
import ptit.base.MyUserDetails;
import ptit.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/user")
    public ResponseEntity<Object> getCurrentUser() {
        MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(user.getUserEntity());
    }

    // get list user
    @GetMapping("/list-user")
    public BaseResponse<Object> getListUser(@RequestParam(required = false, defaultValue = "") String username){
        List<UserEntity> userEntityList = userService.findAllByUsername(username);
        return BaseResponse.builder().code("200").message("Thành công.").body(userEntityList).build();
    }

}
