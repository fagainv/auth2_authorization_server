package com.om.auth2.as.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.om.auth2.as.model.UserRole;
import com.om.auth2.as.repository.UserRoleRepository;
import com.om.auth2.as.service.UserRoleService;

public class UserRoleServiceImpl implements UserRoleService {
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public List<UserRole> findAllByUsername(String username) {
		return userRoleRepository.findAllByUsername(username);
	}

}
