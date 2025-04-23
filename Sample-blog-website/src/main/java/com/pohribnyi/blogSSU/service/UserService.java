package com.pohribnyi.blogSSU.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pohribnyi.blogSSU.model.User;
import com.pohribnyi.blogSSU.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User registerUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public void save(User user) {
		userRepository.save(user);
	}

	public Optional<User> findByResetToken(String token) {
		return userRepository.findByResetToken(token);
	}

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public void updateUserProfile(String email, User updatedUser) {
		User user = findByEmail(email).get();
		user.setFirstName(updatedUser.getFirstName());
		user.setLastName(updatedUser.getLastName());
		user.setAge(updatedUser.getAge());
		user.setGender(updatedUser.getGender());
		userRepository.save(user);
	}

}
