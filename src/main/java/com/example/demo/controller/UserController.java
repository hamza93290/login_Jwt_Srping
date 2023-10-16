package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.Dto.UserInfoDto;
import com.example.demo.Dto.UserInfoMapper;
import com.example.demo.entity.AuthRequest;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.JwtServiceImplementation;
import com.example.demo.service.UserInfoService;

import jakarta.servlet.http.HttpServletRequest; 

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth") 
public class UserController { 

	@Autowired
	private UserInfoService service; 

	@Autowired
	private JwtServiceImplementation jwtService; 

	@Autowired
	private AuthenticationManager authenticationManager; 
	
	@Autowired
	private UserInfoMapper mapper;

//	@GetMapping("/welcome") 
//	@ResponseBody
//	public String welcome() { 
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        
//       // System.out.println(username);
//        return username;
//	} 
	
	@GetMapping("/welcome")
	@ResponseBody
	public Map<String, String> welcome() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    Map<String, String> response = new HashMap<>();
	    response.put("username", username);
	    return response;
	}

	@GetMapping("/userDetail")
	public UserInfoDto findByUsername(HttpServletRequest request) {

		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null & authorizationHeader.startsWith("Bearer ")) {

			String token = authorizationHeader.substring(7);

	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        String username = authentication.getName();

	        UserInfo existingUserInfo = service.findByUsername(username);

	        UserInfoDto existingUserDto = mapper.toDto(existingUserInfo);

			return existingUserDto;

		} else {

			return null;

		}

 

	}
	

	@PostMapping("/addNewUser") 
	public String addNewUser(@RequestBody UserInfo userInfo) { 
		return service.addUser(userInfo); 
	} 

	@GetMapping("/user/userProfile") 
	//@PreAuthorize("hasAuthority('ROLE_USER')") 
	public String userProfile() { 

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);
        return username;
	} 

	@GetMapping("/admin/adminProfile") 
	@PreAuthorize("hasAuthority('ROLE_ADMIN')") 
	public String adminProfile() { 
		return "Welcome to Admin Profile"; 
	} 

	@PostMapping("/generateToken") 
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) { 
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())); 
		if (authentication.isAuthenticated()) { 
			return jwtService.generateToken(authRequest.getUsername()); 
		} else { 
			throw new UsernameNotFoundException("invalid user request !"); 
		} 
	} 

} 

