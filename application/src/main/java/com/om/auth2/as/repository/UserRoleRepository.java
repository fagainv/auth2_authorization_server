package com.om.auth2.as.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.om.auth2.as.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	
	@Query("SELECT ur FROM UserRole ur WHERE UPPER(ur.user.username) like UPPER(:username) ")
	List<UserRole> findAllByUsername(String username);

}
