package com.videocall.springbootbackend.service;

import java.util.List;

import com.videocall.springbootbackend.model.User;

public interface UserService {
	User saveUser(User user);
	List<User> getAllUser();
}
