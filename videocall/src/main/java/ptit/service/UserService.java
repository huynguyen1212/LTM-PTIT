package ptit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ptit.base.MyUserDetails;
import ptit.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserServiceInterface {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity addUser(UserEntity user) {
        UserEntity newUser = userRepository.save(user);
        return newUser;
    }

    @Override
    public UserEntity findOne(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        return user;
    }

    @Override
    public UserEntity findByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserEntity> findAllByUsername(String username) {
        List<UserEntity> list = userRepository.findAllByUsername(username);
        return list;
    }

    @Override
    public UserEntity setOnline(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if(user != null){
            user.setOnline(true);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    @Transactional
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("Not found username : " + username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new MyUserDetails(user.getUsername(), user.getPassword(), true, true, true, true, authorities, user);
    }
}
