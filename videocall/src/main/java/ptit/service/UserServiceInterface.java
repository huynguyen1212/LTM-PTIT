package ptit.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserServiceInterface extends UserDetailsService {
    public UserEntity addUser(UserEntity user);
    public UserEntity findOne(Long id);
    public UserEntity findByEmail(String email);
    public UserEntity findByUsername(String username);
    public List<UserEntity> findAllByUsername(String username);
    public UserEntity setOnline(Long id);
}
