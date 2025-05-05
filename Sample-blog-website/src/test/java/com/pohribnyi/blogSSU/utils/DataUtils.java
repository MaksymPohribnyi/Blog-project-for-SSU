package com.pohribnyi.blogSSU.utils;

import java.time.LocalDateTime;

import com.pohribnyi.blogSSU.model.Comment;
import com.pohribnyi.blogSSU.model.Post;
import com.pohribnyi.blogSSU.model.User;

public class DataUtils {

	public static Post getWalpaperPostTransient() {
		return Post.builder()
				.title("Walpaper")
				.description("Walpaper")
				.content("This is content for Walpaper post")
				.author(null)
				.createdAt(LocalDateTime.of(2025, 5, 5, 13, 27))
				.build();
	}
	
	public static Post getNewspaperPostTransient() {
		return Post.builder()
				.title("Newspaper")
				.description("Newspaper")
				.content("This is content for Newspaper post")
				.author(null)
				.createdAt(LocalDateTime.of(2025, 5, 4, 16, 54))
				.build();
	}
	
	public static Post getWalpaperPostPersisted() {
		return Post.builder()
				.id(1L)
				.title("Walpaper")
				.description("Walpaper")
				.content("This is content for Walpaper post")
				.author(null)
				.createdAt(LocalDateTime.of(2025, 5, 5, 13, 27))
				.build();
	}
	
	public static Post getNewspaperPostPersisted() {
		return Post.builder()
				.id(2L)
				.title("Newspaper")
				.description("Newspaper")
				.content("This is content for Newspaper post")
				.author(null)
				.createdAt(LocalDateTime.of(2025, 5, 4, 16, 54))
				.build();
	}

	public static Comment getCommentTransient() {
		return Comment.builder()
				.content("my test comment")
				.user(null)
				.post(null)
				.build();
	}
	
	public static Comment getLatestCommentTransient() {
		return Comment.builder()
				.content("my test latest comment")
				.user(null)
				.post(Post.builder().id(1L).build())
				.createdAt(LocalDateTime.of(2025, 5, 4, 16, 54))
				.build();
	}
	
	public static Comment getInitialCommentTransient() {
		return Comment.builder()
				.content("my test initial comment")
				.user(null)
				.post(Post.builder().id(1L).build())
				.createdAt(LocalDateTime.of(2025, 5, 5, 13, 27))
				.build();
	}
	
	public static User getUserTransient() {
		return User.builder()
				.username("TestUser")
				.firstName("Test")
				.lastName("User")
				.email("Test_Email@mail.com")
				.password("*******")
				.resetToken("TokenUID1234567890")
				.gender("MALE")
				.age((byte) 33)
				.build();
	}
	
}
