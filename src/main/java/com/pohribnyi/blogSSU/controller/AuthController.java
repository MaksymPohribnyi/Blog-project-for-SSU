package com.pohribnyi.blogSSU.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pohribnyi.blogSSU.model.User;
import com.pohribnyi.blogSSU.service.UserService;

@Controller
@RequestMapping("/blog-SSU/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {
		if (error != null) {
			model.addAttribute("errorMessage", "Невірний email або пароль.");
		}
		if (logout != null) {
			model.addAttribute("logoutMessage", "Ви успішно вийшли з системи.");
		}
		return "login";
	}

	@GetMapping("/register")
	public String registerPage() {
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
		if (userService.findByEmail(user.getEmail()).isPresent()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Користувач з таким email вже існує.");
			return "redirect:/blog-SSU/auth/register";
		}
		userService.registerUser(user);
		redirectAttributes.addFlashAttribute("successRegistration", "Реєстрація успішна! Увійдіть до свого акаунта.");
		return "redirect:/blog-SSU/auth/login";
	}

	@GetMapping("/check-auth")
	@ResponseBody
	public String checkAuth() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.toString();
	}

}
