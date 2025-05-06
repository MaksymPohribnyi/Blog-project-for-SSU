package com.pohribnyi.blogSSU.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pohribnyi.blogSSU.model.Comment;
import com.pohribnyi.blogSSU.model.Post;
import com.pohribnyi.blogSSU.model.User;
import com.pohribnyi.blogSSU.service.CommentService;
import com.pohribnyi.blogSSU.service.PostService;
import com.pohribnyi.blogSSU.service.UserService;

@Controller
@RequestMapping("/blog-SSU/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@Autowired
	private CommentService commentService;

	@GetMapping
	public String showCreatePostForm(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean isAuthenticated = auth != null && auth.isAuthenticated()
				&& !auth.getPrincipal().equals("anonymousUser");
		model.addAttribute("isAuthenticated", isAuthenticated);
		model.addAttribute("post", Post.builder().build());
		return "create-post";
	}

	@PostMapping
	public String createPost(@ModelAttribute Post post, Principal principal) {
		String userEmail = principal.getName();
		User user = userService.findByEmail(userEmail).get();
		post.setAuthor(user);
		post.setCreatedAt(LocalDateTime.now());
		postService.createPost(post);
		return "redirect:/blog-SSU";
	}

	@GetMapping("/{id}")
	public String viewPost(@PathVariable("id") Long id, Model model) {
		Post post = postService.getPostById(id);
		List<Comment> comments = commentService.findByPostIdOrderByCreatedAtAsc(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean isAuthenticated = auth != null && auth.isAuthenticated()
				&& !auth.getPrincipal().equals("anonymousUser");
		model.addAttribute("isAuthenticated", isAuthenticated);
		model.addAttribute("post", post);
		model.addAttribute("comments", comments);
		return "post-view";
	}

}
