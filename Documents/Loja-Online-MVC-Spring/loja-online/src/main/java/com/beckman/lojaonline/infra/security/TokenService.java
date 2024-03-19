package com.beckman.lojaonline.infra.security;

import java.security.AlgorithmConstraints;
import java.security.AlgorithmParameterGeneratorSpi;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.beckman.lojaonline.domain.user.Users;

@Service
public class TokenService {
	@Value("${api.security.token.secret}")
	private String secret;
public String generateToken(Users user) {
	try {
		Algorithm algorthm = Algorithm.HMAC256(secret);
		String token = JWT.create()
				.withIssuer("auth-api").
				withSubject(user.getName())
				.withExpiresAt(generateExpirationDate())
				.sign(algorthm);
		return token;
	}catch(JWTCreationException e){
		throw new RuntimeException("Error while generating token:", e);
		
	}

}	
public String validateToken(String token) {
		try {
			System.out.println("ta indo vazio");
			Algorithm algorthm = Algorithm.HMAC256(secret);
			return JWT.require(algorthm).withIssuer("auth-api").build().verify(token).getSubject();
			
		} catch (JWTVerificationException e) {
			
			return "";
		}
	}
private  Instant generateExpirationDate() {
	return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("+03:00"));

}
}
