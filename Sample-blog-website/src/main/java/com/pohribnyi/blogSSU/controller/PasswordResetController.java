package com.pohribnyi.blogSSU.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pohribnyi.blogSSU.model.User;
import com.pohribnyi.blogSSU.service.PasswordResetService;
import com.pohribnyi.blogSSU.service.UserService;

@Controller
@RequestMapping("/blog-SSU/password")
public class PasswordResetController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordResetService passwordResetService;

	@GetMapping("/forgot")
	public String showForgotPasswordPage() {
		return "forgot-password";
	}

	@PostMapping("/forgot")
	public String processForgotPassword(@RequestParam("email") String email) {
		if (passwordResetService.sendResetLink(email))
			return "redirect:/blog-SSU/password/forgot?success";
		else
			return "redirect:/blog-SSU/password/forgot?error";

	}

	@GetMapping("/reset")
	public String showResetPasswordPage(@RequestParam("token") String token, Model model) {
		Optional<User> userOptional = userService.findByResetToken(token);

		if (userOptional.isPresent()) {
			model.addAttribute("token", token);
			return "reset-password";
		} else {
			return "redirect:/blog-SSU/password/reset?error";
		}
	}

	@PostMapping("/reset")
	public String processResetPassword(@RequestParam("token") String token,
			@RequestParam("newPassword") String newPassword) {
		if (passwordResetService.setNewPassword(token, newPassword))
			return "redirect:/blog-SSU/auth/login?resetSuccess";
		else
			return "redirect:/blog-SSU/password/reset?error";
	}

}