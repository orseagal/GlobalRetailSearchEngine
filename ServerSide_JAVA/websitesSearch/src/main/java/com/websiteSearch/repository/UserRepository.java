package com.websiteSearch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.websiteSearch.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);
	
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	User findByUsernameOrEmail(String userName,String email);
	User getByUsername(String userName);
	
	
}
