package com.videocall.springbootbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.videocall.springbootbackend.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    User findByEmail(String email);
    User findByUsername(String username);

    @Query(value = "select user from User user where user.username like %:username%")
    List<User> findAllByUsername(String username);
}
