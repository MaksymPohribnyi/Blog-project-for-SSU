package com.pohribnyi.blogSSU.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pohribnyi.blogSSU.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByPostIdOrderByCreatedAtAsc(Long postId);
	
}
