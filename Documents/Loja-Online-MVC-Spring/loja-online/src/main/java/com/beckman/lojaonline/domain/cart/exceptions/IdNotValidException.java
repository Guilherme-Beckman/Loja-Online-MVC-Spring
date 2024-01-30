package com.beckman.lojaonline.domain.cart.exceptions;

public class IdNotValidException extends RuntimeException{
	public IdNotValidException() {
		super("Id can't be null or 0");
	}
}
