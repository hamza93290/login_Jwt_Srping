package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserInfo;
import com.example.demo.mapper.MapperMyBatis;
import com.example.demo.repository.UserInfoRepository; 

@Service
@Lazy
public class UserInfoService implements UserDetailsService { 

	@Autowired
	private UserInfoRepository repository; 

	@Autowired
	private PasswordEncoder encoder; 
	
	@Autowired
	private MapperMyBatis mapperMyBatis;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 

		Optional<UserInfo> userDetail = repository.findByName(username); 

		// Converting userDetail to UserDetails 
		System.out.println(userDetail);
		return userDetail.map(UserInfoDetails::new) 
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
	} 

	public UserInfo findByUsername(String name) {

		if(name != null && !name.isBlank()) {

			Optional<UserInfo> existingUser = mapperMyBatis.findByName(name);

			return existingUser.orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", name)));

		}

		return null;

	}
	public String addUser(UserInfo userInfo) { 
		userInfo.setPassword(encoder.encode(userInfo.getPassword())); 
		repository.save(userInfo); 
		return "User Added Successfully"; 
	} 


} 
