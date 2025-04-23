package com.pohribnyi.blogSSU.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pohribnyi.blogSSU.exception.PostNotFoundException;
import com.pohribnyi.blogSSU.model.Post;
import com.pohribnyi.blogSSU.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	public List<Post> getAllPosts() {
		return postRepository.findAll();
	}

	public Post createPost(Post post) {
		return postRepository.save(post);
	}

	public List<Post> getAllPostsOrderByDate() {
		return postRepository.findAllByOrderByCreatedAtDesc();
	}

	public Post getPostById(Long id) {
		return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found by id: " + id));
	}

}
