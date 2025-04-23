package com.pohribnyi.blogSSU.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pohribnyi.blogSSU.model.User;
import com.pohribnyi.blogSSU.service.UserService;

@Controller
@RequestMapping("/blog-SSU/profile")
public class ProfileController {

	@Autowired
	private UserService userService;

	@GetMapping
	public String showProfile(Model model, Principal principal) {
		User user = userService.findByEmail(principal.getName()).get();
		model.addAttribute("user", user);
		return "profile";
	}

	@PostMapping
	public String updateProfile(@ModelAttribute User updatedUser, Principal principal) {
		userService.updateUserProfile(principal.getName(), updatedUser);
		return "redirect:/blog-SSU/profile?success";
	}
}