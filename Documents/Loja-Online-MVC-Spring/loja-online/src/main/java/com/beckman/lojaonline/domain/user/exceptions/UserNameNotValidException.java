package com.beckman.lojaonline.domain.user.exceptions;

public class UserNameNotValidException extends RuntimeException {
	public UserNameNotValidException() {
		super("Username cannot be empty or blank");
	}
}
