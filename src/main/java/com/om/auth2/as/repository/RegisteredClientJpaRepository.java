package com.om.auth2.as.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.om.auth2.as.model.RegisteredClientEntity;

public interface RegisteredClientJpaRepository extends JpaRepository<RegisteredClientEntity, Long> {
	
	
	Optional<RegisteredClientEntity> findByClientId(String clientId);

}
