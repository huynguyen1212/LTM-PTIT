package com.videocall.springbootbackend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

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
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

}
