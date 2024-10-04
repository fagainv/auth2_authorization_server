package com.om.auth2.as.service;

import com.om.auth2.as.model.User;

public interface UserService {

	
	User findByUsername(String username);
}
