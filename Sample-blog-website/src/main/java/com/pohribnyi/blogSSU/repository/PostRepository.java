package com.pohribnyi.blogSSU.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pohribnyi.blogSSU.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAllByOrderByCreatedAtDesc();

}
