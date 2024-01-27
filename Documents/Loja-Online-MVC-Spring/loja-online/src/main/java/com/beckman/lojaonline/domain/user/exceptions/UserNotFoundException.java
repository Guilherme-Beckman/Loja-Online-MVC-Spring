package com.beckman.lojaonline.domain.user.exceptions;

public class UserNotFoundException extends RuntimeException{
	public UserNotFoundException() {
		super("User not found");
	}
}
