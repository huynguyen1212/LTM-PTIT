package com.videocall.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.videocall.springbootbackend.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
