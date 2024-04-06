package com.blogapp.blog_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blogapp.blog_app.dto.JWTAuthResponse;
import com.blogapp.blog_app.dto.LoginDto;
import com.blogapp.blog_app.dto.RegisterDto;
import com.blogapp.blog_app.entity.Token;
import com.blogapp.blog_app.entity.TokenType;
import com.blogapp.blog_app.entity.User;
import com.blogapp.blog_app.repository.TokenRepository;
import com.blogapp.blog_app.repository.UserRepository;
import com.blogapp.blog_app.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService{

	private final AuthenticationManager authenticationManager;
	
    private final UserRepository userRepository;
	
	private final TokenRepository tokenRepository;
    
	private final PasswordEncoder passwordEncoder;
    
	private final JwtService jwtService;

    
	public JWTAuthResponse registerUser(RegisterDto registerDto) {
		var user = User.builder().name(registerDto.getName()).username(registerDto.getUsername())
				.email(registerDto.getEmail()).password(passwordEncoder.encode(registerDto.getPassword()))
				.role(registerDto.getRole()).build();
		var savedUser = userRepository.save(user);
		var jwtToken = jwtService.generateToken(user);
		//var refreshToken = jwtService.generateRefreshToken(user);
		saveUserToken(savedUser, jwtToken);
		return JWTAuthResponse.builder()
				.accessToken(jwtToken)
				//.refreshToken(refreshToken)
				.build();
	}

	public JWTAuthResponse loginUser(LoginDto loginDto) {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		var user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		//var refreshToken = jwtService.generateRefreshToken(user);
		revokeAllUserTokens(user);
		saveUserToken(user, jwtToken);
		return JWTAuthResponse.builder()
				.accessToken(jwtToken)
				//.refreshToken(refreshToken)
				.build();
	}

	public void saveUserToken(User user, String jwtToken) {
		var token = Token.builder()
				.user(user)
				.token(jwtToken)
				.tokenType(TokenType.BEARER)
				.expired(false)
				.revoked(false)
				.build();
		tokenRepository.save(token);
	}

	private void revokeAllUserTokens(User user) {
		var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
		if (validUserTokens.isEmpty())
			return;
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});
		tokenRepository.saveAll(validUserTokens);
	}
}
