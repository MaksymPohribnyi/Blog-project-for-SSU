package com.pohribnyi.blogSSU.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pohribnyi.blogSSU.model.Comment;
import com.pohribnyi.blogSSU.model.Post;
import com.pohribnyi.blogSSU.model.User;
import com.pohribnyi.blogSSU.service.CommentService;
import com.pohribnyi.blogSSU.service.PostService;
import com.pohribnyi.blogSSU.service.UserService;

@Controller
@RequestMapping("/blog-SSU/comments")
public class CommentController {

	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;

	@Autowired
	private CommentService commentService;

	@PostMapping("/{id}")
	public String addComment(@PathVariable("id") Long postId, @RequestParam("content") String content,
			Principal principal) {
		if (principal == null) {
			return "redirect:/blog-SSU/auth/login";
		}

		User user = userService.findByEmail(principal.getName()).orElse(null);
		Post post = postService.getPostById(postId);

		if (user != null && post != null && !content.isBlank()) {
			Comment comment = new Comment();
			comment.setContent(content);
			comment.setUser(user);
			comment.setPost(post);
			commentService.saveComment(comment);
		}

		return "redirect:/blog-SSU/posts/" + postId;
	}

}
