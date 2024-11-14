package com.om.auth2.as.service;

import java.util.List;

import com.om.auth2.as.model.UserRole;

public interface UserRoleService {
	
	/**
	 * Returns all {@link UserRole} objects for the given {@link UserRole#User#Username}
	 * @param username must not be null or empty
	 * @return a list of UserRole
	 */
	
	List<UserRole> findAllByUsername(String username);

}
