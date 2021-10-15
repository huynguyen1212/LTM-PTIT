package ptit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ptit.base.BaseResponse;
import ptit.base.JwtUtils;
import ptit.base.MyUserDetails;
import ptit.dto.AuthLoginDto;
import ptit.dto.AuthSignupDto;
import ptit.service.UserService;

@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public BaseResponse<Object> login(@RequestBody AuthLoginDto login) {
        UserEntity user = userService.findByUsername(login.getUsername());

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
        UserEntity checkExist = userService.findByUsername(signup.getUsername());

        if(checkExist != null){
            return BaseResponse.builder().message("Username đã tồn tại.").code("400").body(null).build();
        }

        UserEntity newUser = new UserEntity();
        newUser.setUsername(signup.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(signup.getPassword1()));
        UserEntity res = userService.addUser(newUser);

        if(res.getId() != null){
            return BaseResponse.builder().message("Đăng ký tài khoản thành công.").code("200").body(res).build();
        }

        return null;
    }
}
