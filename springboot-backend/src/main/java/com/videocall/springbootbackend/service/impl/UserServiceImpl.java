package com.videocall.springbootbackend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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

}
