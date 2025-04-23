package com.pohribnyi.blogSSU.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pohribnyi.blogSSU.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsername(String username);

	Optional<User> findByResetToken(String resetToken);

}
