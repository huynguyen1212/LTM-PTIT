package com.videocall.springbootbackend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videocall.springbootbackend.model.User;
import com.videocall.springbootbackend.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	private UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	//build create user REST API
	@PostMapping("/register")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.CREATED);
	}

	//build get all user REST API
	@GetMapping
	public List<User> getAllUser() {
		return userService.getAllUser();
	}

	//build get user by id REST API
	@GetMapping("{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") int userId) {
		return new ResponseEntity<User>(userService.getUserById(userId), HttpStatus.OK);
	}
	
	//build update user by id REST API
	@PutMapping("{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User user) {
		return new ResponseEntity<User>(userService.updateUser(user, id), HttpStatus.OK); 
	}
	
	//build delete user by id REAT API
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteUser (@PathVariable("id") int id) {
		userService.deleteUser(id);
		return new ResponseEntity<String>("Delete user success", HttpStatus.OK);
	}
}
