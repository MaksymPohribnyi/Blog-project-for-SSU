package com.pohribnyi.blogSSU.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pohribnyi.blogSSU.model.User;

@Service
public class PasswordResetService {

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public boolean sendResetLink(String email) {
		Optional<User> userOpt = userService.findByEmail(email);
		if (userOpt.isEmpty()) {
			return false;
		}

		User user = userOpt.get();
		String token = UUID.randomUUID().toString();
		user.setResetToken(token);
		userService.save(user);

		String resetLink = "http://localhost:8787/blog-SSU/password/reset?token=" + token;
		emailService.sendEmail(user.getEmail(), "Cкидання пароля", resetLink);
		return true;
	}

	public boolean setNewPassword(String token, String newPassword) {
		Optional<User> userOptional = userService.findByResetToken(token);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			user.setPassword(passwordEncoder.encode(newPassword));
			user.setResetToken(null);
			userService.save(user);
			return true;
		}
		return false;
	}
}
