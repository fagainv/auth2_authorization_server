package com.om.auth2.as.service.impl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.om.auth2.as.model.User;
import com.om.auth2.as.service.UserService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("UserDetailsServiceImpl.loadUserByUsername method");
		log.debug("loadUserByUsername(username): {}", username);
		
		try {
			User user = userService.findByUsername(username);
			if (null == user || user.getActive() == null || !user.getActive().equalsIgnoreCase("A")) {
				throw new UsernameNotFoundException("User not found for username: " + username);
			}
					
			
			return new org.springframework.security.core.userdetails.User(user.getUsername(),
					user.getPassword().startsWith("{bcrypt}") ? user.getPassword() : "{bcrypt}" + user.getPassword(), 
					user.getUserRoles().stream().map(ur -> new SimpleGrantedAuthority(
							ur.getCustomerRole().getRole().startsWith("ROLE_") 
									? ur.getCustomerRole().getRole() 
									: "ROLE_" + ur.getCustomerRole().getRole()
							)).collect(Collectors.toSet())
					);
		}
		catch(Exception e) {
			log.error("An error happend when trying to loadUserByUsername with message: " + e.getMessage());
			throw new UsernameNotFoundException("User not found for username");
		}
	}

}
