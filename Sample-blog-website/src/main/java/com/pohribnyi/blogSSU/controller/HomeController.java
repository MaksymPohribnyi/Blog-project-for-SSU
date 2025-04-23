package com.pohribnyi.blogSSU.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pohribnyi.blogSSU.service.PostService;

@Controller
@RequestMapping("/blog-SSU")
public class HomeController {

	@Autowired
	private PostService postService;

	@GetMapping
	public String homePage(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean isAuthenticated = auth != null && auth.isAuthenticated()
				&& !auth.getPrincipal().equals("anonymousUser");
		model.addAttribute("isAuthenticated", isAuthenticated);
		model.addAttribute("posts", postService.getAllPostsOrderByDate());
		return "home";
	}

}
