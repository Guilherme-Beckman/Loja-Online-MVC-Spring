package com.beckman.lojaonline.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.beckman.lojaonline.domain.user.AuthenticationDTO;
import com.beckman.lojaonline.domain.user.RegisterDTO;
import com.beckman.lojaonline.domain.user.UserRole;
import com.beckman.lojaonline.domain.user.Users;
import com.beckman.lojaonline.infra.security.TokenService;
import com.beckman.lojaonline.repositories.UserRepository;
import com.beckman.lojaonline.services.UserService;
import jakarta.validation.Valid;


@Service
public class AuthenticationService {
	@Autowired 
	UserRepository repository;
	@Autowired
	UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired 
	private TokenService tokenService;


public Boolean register (@RequestBody @Valid RegisterDTO data) {

	if (this.repository.findByName(data.name()) != null) {

		return false;
	}else {
		
		RegisterDTO register = new RegisterDTO(data.name(),data.password(), UserRole.USER);
		Users user = this.userService.insert(register);
		return true;
	}
}

	public String login(@RequestBody @Valid AuthenticationDTO data) {
	if(data.name()!=null) {
		try {
		var usernamePassword= new UsernamePasswordAuthenticationToken(data.name(), data.password());
			var auth = this.authenticationManager.authenticate(usernamePassword);
			var token = tokenService.generateToken((Users) auth.getPrincipal());
			return token;
		}catch(Exception e) {
			return null;
		}
			
	}else {
		return "Preencha com suas credenciais";
	}
		
		
	}

}
