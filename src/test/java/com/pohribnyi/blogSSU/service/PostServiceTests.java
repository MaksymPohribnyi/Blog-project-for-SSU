package com.pohribnyi.blogSSU.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;

import com.pohribnyi.blogSSU.exception.PostNotFoundException;
import com.pohribnyi.blogSSU.model.Post;
import com.pohribnyi.blogSSU.repository.PostRepository;
import com.pohribnyi.blogSSU.utils.DataUtils;

@ExtendWith(MockitoExtension.class)
public class PostServiceTests {

	@Mock
	private PostRepository postRepository;

	@InjectMocks
	private PostService postServiceUnderTest;

	@Test
	@DisplayName("Test of save Post functionality")
	public void given_PostToSave_whenSavePost_thenRepositoryIsCalled() {

		// given
		Post postToSave = DataUtils.getNewspaperPostTransient();

		BDDMockito.given(postRepository.save(any(Post.class))).willReturn(DataUtils.getNewspaperPostPersisted());

		// when
		Post savedPost = postServiceUnderTest.createPost(postToSave);

		// then
		assertThat(savedPost).isNotNull();
		verify(postRepository, atLeastOnce()).save(any(Post.class));

	}

	@Test
	@DisplayName("Test of get Post by id functionality")
	public void given_PostId_whenGetPostById_thenPostIsReturned() {

		// given
		BDDMockito.given(postRepository.findById(anyLong()))
				.willReturn(Optional.of(DataUtils.getNewspaperPostPersisted()));
		// when
		Post obtainedPost = postServiceUnderTest.getPostById(1L);
		// then
		assertThat(obtainedPost).isNotNull();
		verify(postRepository, atLeastOnce()).findById(anyLong());

	}

	@Test
	@DisplayName("Test of get Post by incorrect id functionality")
	public void given_IncorrectPostId_whenGetPostById_thenExceptionIsThrown() {

		// given
		BDDMockito.given(postRepository.findById(anyLong())).willReturn(Optional.empty());
		// when
		// then
		assertThrows(PostNotFoundException.class, () -> postServiceUnderTest.getPostById(1L));
		verify(postRepository, atLeastOnce()).findById(1L);

	}

	@Test
	@DisplayName("Test get all Posts functionality")
	public void given_TwoPosts_whenGetAll_thenTwoPostsAreReturned() {

		// given
		Post newspaperPost = DataUtils.getNewspaperPostPersisted();
		Post walpaperPost = DataUtils.getWalpaperPostPersisted();

		List<Post> listOfPosts = List.of(newspaperPost, walpaperPost);

		BDDMockito.given(postRepository.findAll()).willReturn(listOfPosts);
		// when

		List<Post> obtainedListOfPosts = postServiceUnderTest.getAllPosts();
		// then

		assertThat(CollectionUtils.isEmpty(obtainedListOfPosts)).isFalse();
		assertThat(obtainedListOfPosts.size()).isEqualTo(2);

	}
	
	@Test
	@DisplayName("Test get all order by date Post functionality")
	public void given_TwoPosts_whenGetAllOrderByDate_thenTwoPostsOrderByDateAreReturned() {

		// given
		Post newspaperPost = DataUtils.getNewspaperPostPersisted();
		Post walpaperPost = DataUtils.getWalpaperPostPersisted();

		List<Post> listOfPosts = List.of(newspaperPost, walpaperPost);

		BDDMockito.given(postRepository.findAllByOrderByCreatedAtDesc()).willReturn(listOfPosts);
		// when

		List<Post> obtainedListOfPosts = postServiceUnderTest.getAllPostsOrderByDate();
		// then

		assertThat(CollectionUtils.isEmpty(obtainedListOfPosts)).isFalse();
		assertThat(obtainedListOfPosts.size()).isEqualTo(3);

	}
	

}
