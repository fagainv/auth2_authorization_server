package com.om.auth2.as.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.om.auth2.as.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsername(String username);

}
