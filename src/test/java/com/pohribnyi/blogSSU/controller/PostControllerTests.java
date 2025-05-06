package com.pohribnyi.blogSSU.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.pohribnyi.blogSSU.model.Comment;
import com.pohribnyi.blogSSU.model.Post;
import com.pohribnyi.blogSSU.model.User;
import com.pohribnyi.blogSSU.service.CommentService;
import com.pohribnyi.blogSSU.service.PostService;
import com.pohribnyi.blogSSU.service.UserService;
import com.pohribnyi.blogSSU.utils.DataUtils;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PostControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private PostService postService;

	@MockitoBean
	private UserService userService;

	@MockitoBean
	private CommentService commentService;

	@Test
	@DisplayName("Test show create post view functionality")
	public void given_CreatePostUrl_whenGetCreatePostUrl_thenReturnsPostView() throws Exception {

		// given
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/blog-SSU/posts")).andReturn();
		// when

		// then
		var mvcResult = result.getResponse();

		assertThat(mvcResult.getStatus()).isEqualTo(200);
		assertThat(result.getModelAndView()).isNotNull();
		assertThat(result.getModelAndView().getViewName()).isEqualTo("create-post");
		assertThat(result.getModelAndView().getModel()).containsKeys("post", "isAuthenticated");
	}

	@Test
	@DisplayName("Test create post functionality")
	void givenValidPostAndUser_whenCreatePost_thenRedirectsAndSavesPost() throws Exception {
		// given
		User authUser = DataUtils.getUserPersisted();
		Principal mockPrincipal = () -> authUser.getEmail();

		BDDMockito.given(userService.findByEmail(anyString())).willReturn(Optional.of(authUser));

		// when
		var result = mockMvc.perform(MockMvcRequestBuilders.post("/blog-SSU/posts").param("title", "Test Title")
				.param("content", "Test Content").principal(mockPrincipal)).andReturn();

		// then
		var response = result.getResponse();
		assertThat(response.getStatus()).isBetween(300, 399);
		assertThat(response.getRedirectedUrl()).isEqualTo("/blog-SSU");

		verify(postService, atLeastOnce()).createPost(any(Post.class));
	}

	@Test
	@DisplayName("Test post view functionality")
	void givenPostAndComments_whenViewPost_thenReturnsPostViewWithModel() throws Exception {
		// given
		Post mockPost = DataUtils.getNewspaperPostPersisted();
		List<Comment> mockComments = List.of(DataUtils.getInitialCommentPersisted(),
				DataUtils.getLatestCommentPersisted());

		BDDMockito.given(postService.getPostById(anyLong())).willReturn(mockPost);
		BDDMockito.given(commentService.findByPostIdOrderByCreatedAtAsc(anyLong())).willReturn(mockComments);

		// when
		var result = mockMvc.perform(MockMvcRequestBuilders.get("/blog-SSU/posts/{id}", 1L)).andReturn();

		// then
		var response = result.getResponse();
		var model = result.getModelAndView().getModel();

		assertThat(response.getStatus()).isEqualTo(200);
		assertThat(result.getModelAndView().getViewName()).isEqualTo("post-view");

		assertThat(model).containsEntry("post", mockPost).containsEntry("comments", mockComments)
				.containsKey("isAuthenticated");

		verify(postService, atLeastOnce()).getPostById(anyLong());
		verify(commentService, atLeastOnce()).findByPostIdOrderByCreatedAtAsc(anyLong());

	}

}
