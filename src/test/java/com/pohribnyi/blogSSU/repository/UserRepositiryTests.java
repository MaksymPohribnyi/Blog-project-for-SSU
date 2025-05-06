package com.pohribnyi.blogSSU.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.pohribnyi.blogSSU.model.User;
import com.pohribnyi.blogSSU.utils.DataUtils;

@DataJpaTest
public class UserRepositiryTests {

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	public void setUp() {
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("Test save User functionality")
	public void given_UserObjectwhenSaveUser_thenUserIsCreated() {

		// given
		User UserToSave = DataUtils.getUserTransient();
		// when
		userRepository.save(UserToSave);
		// then
		assertThat(UserToSave).isNotNull();
		assertThat(UserToSave.getId()).isNotNull();

	}

	@Test
	@DisplayName("Test update User functionality")
	public void given_UserObjectwhenUpdateUser_thenUserIsUpdated() {

		// given
		User userToSave = DataUtils.getUserTransient();
		userRepository.save(userToSave);

		// when
		String updUsername = "UPDATE! New_TestUser";
		userToSave.setUsername(updUsername);
		User updatedUser = userRepository.save(userToSave);

		// then
		assertThat(updatedUser).isNotNull();
		assertThat(userToSave.getUsername()).isEqualTo(updUsername);

	}

	@Test
	@DisplayName("Test find User by id functionality")
	public void given_UserIdwhenFindUserById_thenUserIsReturned() {

		// given
		User userToSave = DataUtils.getUserTransient();
		userRepository.save(userToSave);

		// when
		User obtainedUser = userRepository.findById(userToSave.getId()).orElse(null);

		// then
		assertThat(obtainedUser).isNotNull();

	}

	@Test
	@DisplayName("Test User not found functionality")
	public void given_UserIdwhenFindUserById_thenUserIsNull() {

		// given
		// when
		User obtainedUser = userRepository.findById(1L).orElse(null);

		// then
		assertThat(obtainedUser).isNull();

	}
	
	@Test
	@DisplayName("Test find User by email functionality")
	public void given_UserEmailwhenFindByEmail_thenUserIsReturned() {

		// given
		User userToSave = DataUtils.getUserTransient();
		userRepository.save(userToSave);

		// when
		User obtainedUser = userRepository.findByEmail(userToSave.getEmail()).orElse(null);

		// then
		assertThat(obtainedUser).isNotNull();

	}

	@Test
	@DisplayName("Test User not found by email functionality")
	public void given_UserEmailwhenFindByEmail_thenUserIsNull() {

		// given
		// when
		User obtainedUser = userRepository.findByEmail(anyString()).orElse(null);

		// then
		assertThat(obtainedUser).isNull();

	}
	
	@Test
	@DisplayName("Test find User by username functionality")
	public void given_UsernamewhenFindByUsername_thenUserIsReturned() {

		// given
		User userToSave = DataUtils.getUserTransient();
		userRepository.save(userToSave);

		// when
		User obtainedUser = userRepository.findByUsername(userToSave.getUsername()).orElse(null);

		// then
		assertThat(obtainedUser).isNotNull();

	}

	@Test
	@DisplayName("Test User not found by username functionality")
	public void given_UsernamewhenFindByUsername_thenUserIsNull() {

		// given
		// when
		User obtainedUser = userRepository.findByUsername(anyString()).orElse(null);

		// then
		assertThat(obtainedUser).isNull();

	}
	
	@Test
	@DisplayName("Test find User by reset token functionality")
	public void given_UserWithTokenwhenFindByResetToken_thenUserIsReturned() {

		// given
		User userToSave = DataUtils.getUserTransient();
		userRepository.save(userToSave);

		// when
		User obtainedUser = userRepository.findByResetToken(userToSave.getResetToken()).orElse(null);

		// then
		assertThat(obtainedUser).isNotNull();

	}

	@Test
	@DisplayName("Test User not found by reset token functionality")
	public void given_UserWithTokenwhenFindByResetToken_thenUserIsNull() {

		// given
		// when
		User obtainedUser = userRepository.findByResetToken(anyString()).orElse(null);

		// then
		assertThat(obtainedUser).isNull();

	}
	
}
