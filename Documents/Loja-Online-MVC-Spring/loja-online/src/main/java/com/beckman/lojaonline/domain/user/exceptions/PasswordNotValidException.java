package com.beckman.lojaonline.domain.user.exceptions;

public class PasswordNotValidException extends RuntimeException {
	public PasswordNotValidException() {
		super("Password cannot be empty or blank");
	}
}
