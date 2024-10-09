package com.om.auth2.as.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.om.auth2.as.model.RegisteredClientEntity;
import com.om.auth2.as.repository.RegisteredClientJpaRepository;
import com.om.auth2.as.service.RegisteredClientJpaService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
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
		log.debug("update registeredClient: {}", registeredClientEntity);
		Optional<RegisteredClientEntity> entityFromDB = 
				registeredClientJpaRepository.findById(registeredClientEntity.getId());
		
		if (!registeredClientEntity.getClientSecret().equals(entityFromDB.get().getClientSecret())) {
			registeredClientEntity.setClientSecret(passwordEncoder.encode(registeredClientEntity.getClientSecret()));			
		}

		return registeredClientJpaRepository.save(registeredClientEntity);
	}

	@Override
	public Page<RegisteredClientEntity> findAll(Pageable pageable) {
		return registeredClientJpaRepository.findAll(pageable);
	}

	@Override
	public void deleteById(Long registeredClientId) {
		log.debug("deleteById registeredClientId: {}", registeredClientId);
		registeredClientJpaRepository.deleteById(registeredClientId);
		
	}

}
