package com.blogapp.blog_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.blog_app.dto.JWTAuthResponse;
import com.blogapp.blog_app.dto.LoginDto;
import com.blogapp.blog_app.dto.RegisterDto;
import com.blogapp.blog_app.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	

	@PostMapping("/register")
	public ResponseEntity<JWTAuthResponse> registerUser(@RequestBody RegisterDto registerRequest) {
		
		return ResponseEntity.ok(authService.registerUser(registerRequest));
	}
	
	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> loginUser(@RequestBody LoginDto loginRequest) {
		
		return ResponseEntity.ok(authService.loginUser(loginRequest));
	}
}
