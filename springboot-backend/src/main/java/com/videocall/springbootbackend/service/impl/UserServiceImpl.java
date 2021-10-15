package com.videocall.springbootbackend.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.videocall.springbootbackend.base.MyUserDetails;
import com.videocall.springbootbackend.exception.ResourceNotFoundException;
import com.videocall.springbootbackend.model.User;
import com.videocall.springbootbackend.repository.UserRepository;
import com.videocall.springbootbackend.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
    @Override
    public User findOne(int id) {
    	User user = userRepository.findById(id).orElse(null);
        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAllByUsername(String username) {
        List<User> list = userRepository.findAllByUsername(username);
        return list;
    }

    @Override
    public User setOnline(int id) {
    	User user = userRepository.findById(id).orElse(null);
        if(user != null){
            user.setOnline(0);
            return userRepository.save(user);
        }
        return null;
    }

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(int id) {
		Optional<User> user = userRepository.findById(id);
		
		if (user.isPresent()) {
			return user.get();
		}else {
			throw new ResourceNotFoundException("User", "Id", id);
		}
	}

	@Override
	public User updateUser(User user, int id) {
		User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
		existingUser.setAddress(user.getAddress());
		existingUser.setAvatar(user.getAvatar());
		existingUser.setCreated_at(user.getCreated_at());
		existingUser.setLast_online(user.getLast_online());
		existingUser.setOnline(user.getOnline());
		existingUser.setPassword(user.getPassword());
		existingUser.setUpdate_at(user.getUpdate_at());
		existingUser.setUsername(user.getUsername());
		
		userRepository.save(existingUser);
		return existingUser;
	}

	@Override
	public void deleteUser(int id) {
		userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
		userRepository.deleteById(id);
	}
	
    //@Override
    @Transactional
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("Not found username : " + username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new MyUserDetails(user.getUsername(), user.getPassword(), true, true, true, true, authorities, user);
    }
}
