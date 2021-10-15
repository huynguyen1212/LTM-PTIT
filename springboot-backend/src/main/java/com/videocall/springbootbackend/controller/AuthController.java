package com.videocall.springbootbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.videocall.springbootbackend.base.BaseResponse;
import com.videocall.springbootbackend.base.JwtUtils;
import com.videocall.springbootbackend.base.MyUserDetails;
import com.videocall.springbootbackend.dto.AuthLoginDto;
import com.videocall.springbootbackend.dto.AuthSignupDto;
import com.videocall.springbootbackend.model.User;
import com.videocall.springbootbackend.service.impl.UserServiceImpl;

public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserServiceImpl userService;
    
    @PostMapping("/login")
    public BaseResponse<Object> login(@RequestBody AuthLoginDto login) {
        User user = UserServiceImpl.findByUsername(login.getUsername());

        if(user != null){
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
            }
            catch (BadCredentialsException e) {
                throw new BadCredentialsException("Invalid username " + login.getUsername());
            }
            MyUserDetails myUserDetails = (MyUserDetails) userService.loadUserByUsername(login.getUsername());
            // set online is true
            userService.setOnline(myUserDetails.getUserEntity().getId());

            String jwt = JwtUtils.generateToken(myUserDetails);
            return BaseResponse.builder().message("Đăng nhập thành công.").code("200").body(jwt).build();
        }

        return BaseResponse.builder().message("Đăng nhập thất bại.").code("400").body(null).build();
    }
    
    @PostMapping("/signup")
    public BaseResponse<Object> signup(@RequestBody AuthSignupDto signup) {
        // check mapping pass
        if(!signup.getPassword1().equals(signup.getPassword2())){
            return BaseResponse.builder().message("Mật khẩu không khớp.").code("400").body(null).build();
        }

        // check username exist
        User checkExist = userService.findByUsername(signup.getUsername());

        if(checkExist != null){
            return BaseResponse.builder().message("Username đã tồn tại.").code("400").body(null).build();
        }

        User newUser = new User();
        newUser.setUsername(signup.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(signup.getPassword1()));
        User res = userService.addUser(newUser);

        if(res.getId() != null){
            return BaseResponse.builder().message("Đăng ký tài khoản thành công.").code("200").body(res).build();
        }

        return null;
    }
}
