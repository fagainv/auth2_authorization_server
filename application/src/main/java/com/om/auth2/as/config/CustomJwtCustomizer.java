package com.om.auth2.as.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import com.om.auth2.as.model.User;
import com.om.auth2.as.service.UserService;

@Component
public class CustomJwtCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

	@Autowired
	private UserService userService;
	
	@Override
	public void customize(JwtEncodingContext context) {
		
		if (context.getPrincipal() != null ) {
			String username = context.getPrincipal().getName();
			User user = userService.findByUsername(username);
			
			// Add the roles to JWT claims
			List<String> roles = user.getUserRoles().stream().map(ur -> ur.getCustomerRole().getRole())
					.collect(Collectors.toList());
			
			context.getClaims().claim("roles", roles);
		}
		
	}

}
