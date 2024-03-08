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
import com.beckman.lojaonline.domain.user.UserRole;
import com.beckman.lojaonline.domain.user.Users;
import com.beckman.lojaonline.infra.security.TokenService;
import com.beckman.lojaonline.repositories.UserRepository;
import com.beckman.lojaonline.services.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	@Autowired 
	UserRepository repository;
	@Autowired
	UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired 
	private TokenService tokenService;

@PostMapping("/register")
public ResponseEntity register (@RequestBody @Valid RegisterDTO data) {
	System.out.println(data.name());
System.out.println("se chegou aqui deu certp");
	if (this.repository.findByName(data.name()) != null) {
		System.out.println(data.name()+"err pq ja tem");
		return ResponseEntity.badRequest().build();
	}else {
		System.out.println(data.name());
		RegisterDTO register = new RegisterDTO(data.name(),data.password(), UserRole.USER);
		Users user = this.userService.insert(register);
		return ResponseEntity.ok().body(user);
	}
}
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
		var usernamePassword= new UsernamePasswordAuthenticationToken(data.name(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((Users) auth.getPrincipal());
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
}
