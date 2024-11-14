package com.om.auth2.as.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.om.auth2.as.model.User;
import com.om.auth2.as.repository.UserRepository;
import com.om.auth2.as.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User findByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}

}
