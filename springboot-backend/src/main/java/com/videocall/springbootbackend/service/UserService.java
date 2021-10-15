package com.videocall.springbootbackend.service;

import java.util.List;

import com.videocall.springbootbackend.model.User;

public interface UserService {
	User saveUser(User user);
	List<User> getAllUser();
	User getUserById(int id);
	User updateUser (User user, int id);
	void deleteUser(int id);
    public User findOne(int id);
    public User findByEmail(String email);
    public User findByUsername(String username);
    public User setOnline(int id);
    public List<User> findAllByUsername(String username);
}
