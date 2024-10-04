package com.om.auth2.as.service;

import com.om.auth2.as.model.RegisteredClientEntity;

public interface RegisteredClientJpaService {
	
	/**
	 * Saves the given {@link RegisteredClientEntity} object and returns it as a managed object
	 * {@link RegisteredClientEntity#clientId} must not be null or blank
	 * {@link RegisteredClientEntity#clientSecret} must not be null or blank
	 * 
	 * @param registeredClientEntity must not be null
	 * @return
	 */
	RegisteredClientEntity save(RegisteredClientEntity registeredClientEntity);

}
