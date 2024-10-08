package com.om.auth2.as.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.om.auth2.as.model.RegisteredClientEntity;
import com.om.auth2.as.repository.RegisteredClientJpaRepository;
import com.om.auth2.as.service.RegisteredClientJpaService;

@Service
public class RegisteredClientJpaServiceImpl implements RegisteredClientJpaService {
	
	@Autowired
	private RegisteredClientJpaRepository registeredClientJpaRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public RegisteredClientEntity save(RegisteredClientEntity registeredClientEntity) {

		
		registeredClientEntity.setClientSecret(passwordEncoder.encode(registeredClientEntity.getClientSecret()));
		return registeredClientJpaRepository.save(registeredClientEntity);
	}

	@Override
	public RegisteredClientEntity update(RegisteredClientEntity registeredClientEntity) {
		return registeredClientJpaRepository.save(registeredClientEntity);
	}

	@Override
	public Page<RegisteredClientEntity> findAll(Pageable pageable) {
		return registeredClientJpaRepository.findAll(pageable);
	}

}
