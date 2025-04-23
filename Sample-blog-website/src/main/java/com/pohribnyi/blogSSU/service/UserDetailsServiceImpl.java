package com.pohribnyi.blogSSU.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pohribnyi.blogSSU.repository.UserRepository;
import com.pohribnyi.blogSSU.security.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email).map(UserDetailsImpl::new)
				.orElseThrow(() -> new UsernameNotFoundException("Користувач з email " + email + " не знайдений"));
	}

}
