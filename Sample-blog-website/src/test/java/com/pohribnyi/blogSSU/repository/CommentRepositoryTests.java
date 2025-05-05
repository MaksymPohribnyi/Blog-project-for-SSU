package com.pohribnyi.blogSSU.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.pohribnyi.blogSSU.model.Comment;
import com.pohribnyi.blogSSU.model.Post;
import com.pohribnyi.blogSSU.utils.DataUtils;

@DataJpaTest
public class CommentRepositoryTests {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@BeforeEach
	public void setUp() {
		commentRepository.deleteAll();
		postRepository.deleteAll();
	}

	@Test
	@DisplayName("Test save Comment functionality")
	public void given_CommentObjectwhenSaveComment_thenCommentIsCreated() {

		// given
		Comment commentToSave = DataUtils.getCommentTransient();
		// when
		commentRepository.save(commentToSave);
		// then
		assertThat(commentToSave).isNotNull();
		assertThat(commentToSave.getId()).isNotNull();

	}

	@Test
	@DisplayName("Test update Comment functionality")
	public void given_CommentObjectwhenUpdateComment_thenCommentIsUpdated() {

		// given
		Comment commentToSave = DataUtils.getCommentTransient();
		commentRepository.save(commentToSave);

		// when
		String updContent = "UPDATE! new test content";
		commentToSave.setContent(updContent);
		Comment updatedComment = commentRepository.save(commentToSave);

		// then
		assertThat(updatedComment).isNotNull();
		assertThat(commentToSave.getContent()).isEqualTo(updContent);

	}

	@Test
	@DisplayName("Test find Comment by id functionality")
	public void given_CommentIdwhenFindCommentById_thenCommentIsReturned() {

		// given
		Comment CommentToSave = DataUtils.getCommentTransient();
		commentRepository.save(CommentToSave);

		// when
		Comment obtainedComment = commentRepository.findById(CommentToSave.getId()).orElse(null);

		// then
		assertThat(obtainedComment).isNotNull();

	}

	@Test
	@DisplayName("Test Comment not found functionality")
	public void given_CommentIdwhenFindCommentById_thenCommentIsNull() {

		// given
		// when
		Comment obtainedComment = commentRepository.findById(1L).orElse(null);

		// then
		assertThat(obtainedComment).isNull();

	}

	@Test
	@DisplayName("Test find all Comments order by Created_at functionality")
	public void given_SeveralCommentswhenFindAllOrderByCreatedAt_thenListOfCommentsIsReturned() {

		// given
		Post newPost = postRepository.save(DataUtils.getPostTransient());

		Comment latestComment = DataUtils.getLatestCommentTransient();
		Comment initialComment = DataUtils.getInitialCommentTransient();

		commentRepository.save(latestComment);
		commentRepository.save(initialComment);

		List<Comment> lisOfComments = new ArrayList<>();
		lisOfComments.add(latestComment);
		lisOfComments.add(initialComment);
		System.out.println(lisOfComments);

		// when
		List<Comment> obtainedComments = commentRepository.findByPostIdOrderByCreatedAtAsc(newPost.getId());
		System.out.println(obtainedComments);

		// then
		assertEquals(lisOfComments, obtainedComments);

	}

}
