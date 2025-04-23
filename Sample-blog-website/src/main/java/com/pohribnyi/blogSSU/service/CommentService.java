package com.pohribnyi.blogSSU.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pohribnyi.blogSSU.model.Comment;
import com.pohribnyi.blogSSU.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	public List<Comment> findByPostIdOrderByCreatedAtAsc(Long id) {
		return commentRepository.findByPostIdOrderByCreatedAtAsc(id);
	}

	public void saveComment(Comment comment) {
		commentRepository.save(comment);
	}

}
