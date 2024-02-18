package com.beckman.lojaonline.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beckman.lojaonline.domain.user.AuthenticationDTO;
import com.beckman.lojaonline.domain.user.LoginResponseDTO;
import com.beckman.lojaonline.domain.user.RegisterDTO;
import com.beckman.lojaonline.domain.user.Users;
import com.beckman.lojaonline.infra.security.TokenService;
import com.beckman.lojaonline.repositories.UserRepository;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	@Autowired 
	UserRepository repository;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired 
	private TokenService tokenService;
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
		var usernamePassword= new UsernamePasswordAuthenticationToken(data.name(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((Users) auth.getPrincipal());
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
@PostMapping("/register")
public ResponseEntity register (@RequestBody @Valid RegisterDTO data) {
	if (this.repository.findByName(data.name()) != null) {
		return ResponseEntity.badRequest().build();
	}else {
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		Users newUser = new Users(data.name(), encryptedPassword, data.role());
		this.repository.save(newUser);
		return ResponseEntity.ok().build();
	}
}

}
