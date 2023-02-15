package com.example.spring03.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring03.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsername(String username);
	
	// auth2 추가
	Optional<User> findByEmail(String email);

}
