package com.cgd.mkt.salary_process.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

//	@Autowired
//	UserRepository userRepository;
	
	@Autowired
	public PasswordEncoder passwordEncoder ;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		String hcUsername = "admin";
		String hcPassword = "12345";
		//get user info from Database
		//User user = userRepository.findByUserName(username);
		if (!username.equals(hcUsername)) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(
				hcUsername, passwordEncoder.encode(hcPassword),
				new ArrayList<>());

	}
	
//	public User getUserFromDB(String username) {
//		User user = userRepository.findByUserName(username);
//		return user;
//	}
}