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

import com.pohribnyi.blogSSU.model.Post;
import com.pohribnyi.blogSSU.utils.DataUtils;

@DataJpaTest
class PostRepositoryTests {

	@Autowired
	private PostRepository postRepository;

	@BeforeEach
	public void setUp() {
		postRepository.deleteAll();
	}

	@Test
	@DisplayName("Test save Post functionality")
	public void given_PostObjectwhenSavePost_thenPostIsCreated() {

		// given
		Post postToSave = DataUtils.getPostTransient();
		// when
		postRepository.save(postToSave);
		// then
		assertThat(postToSave).isNotNull();
		assertThat(postToSave.getId()).isNotNull();

	}

	@Test
	@DisplayName("Test update Post functionality")
	public void given_PostObjectwhenUpdatePost_thenPostIsUpdated() {

		// given
		Post postToSave = DataUtils.getPostTransient();
		postRepository.save(postToSave);

		// when
		String updTitle = "UPDATE! new test title";
		postToSave.setTitle(updTitle);
		Post updatedPost = postRepository.save(postToSave);

		// then
		assertThat(updatedPost).isNotNull();
		assertThat(postToSave.getTitle()).isEqualTo(updTitle);

	}

	@Test
	@DisplayName("Test find Post by id functionality")
	public void given_PostIdwhenFindPostById_thenPostIsReturned() {

		// given
		Post postToSave = DataUtils.getPostTransient();
		postRepository.save(postToSave);

		// when
		Post obtainedPost = postRepository.findById(postToSave.getId()).orElse(null);

		// then
		assertThat(obtainedPost).isNotNull();

	}

	@Test
	@DisplayName("Test Post not found functionality")
	public void given_PostIdwhenFindPostById_thenPostIsNull() {

		// given
		// when
		Post obtainedPost = postRepository.findById(1L).orElse(null);

		// then
		assertThat(obtainedPost).isNull();

	}

	@Test
	@DisplayName("Test find all Posts order by Created_at functionality")
	public void given_SeveralPostswhenFindAllOrderByCreatedAt_thenListOfPostsIsReturned() {

		// given
		Post newspaper = DataUtils.getNewspaperPostTransient();
		Post wallpaper = DataUtils.getWalpaperPostTransient();

		postRepository.save(newspaper);
		postRepository.save(wallpaper);

		List<Post> lisOfPosts = new ArrayList<>();
		lisOfPosts.add(wallpaper);
		lisOfPosts.add(newspaper);
		System.out.println(lisOfPosts);

		// when
		List<Post> obtainedPosts = postRepository.findAllByOrderByCreatedAtDesc();
		System.out.println(obtainedPosts);

		// then
		assertEquals(lisOfPosts, obtainedPosts);

	}

}
