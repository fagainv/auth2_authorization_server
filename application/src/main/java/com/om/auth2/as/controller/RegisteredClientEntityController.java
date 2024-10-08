package com.om.auth2.as.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.om.auth2.as.model.RegisteredClientEntity;
import com.om.auth2.as.service.RegisteredClientJpaService;

@RestController
public class RegisteredClientEntityController {
	
	@Autowired
	private RegisteredClientJpaService registeredClientJpaService;
	
	
	@GetMapping("/registered-client/find-all")
	public Page<RegisteredClientEntity> findAll(Pageable pageable) {
		return registeredClientJpaService.findAll(pageable);
	}
	
	@PostMapping("/registered-client")
	public RegisteredClientEntity save(@RequestBody RegisteredClientEntity registeredClientEntity) {
		return registeredClientJpaService.save(registeredClientEntity);
	}
	@PutMapping("/registered-client")
	public RegisteredClientEntity update(@RequestBody RegisteredClientEntity registeredClientEntity) {
		return registeredClientJpaService.save(registeredClientEntity);
	}


}
