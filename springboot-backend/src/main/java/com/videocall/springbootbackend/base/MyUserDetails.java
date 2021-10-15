package com.videocall.springbootbackend.base;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.videocall.springbootbackend.model.User;

public class MyUserDetails extends org.springframework.security.core.userdetails.User {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User userEntity;

    public MyUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, User userEntity) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userEntity = userEntity;
    }

    public User getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(User userEntity) {
        this.userEntity = userEntity;
    }
}