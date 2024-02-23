package com.beckman.lojaonline.domain.product.exceptions;

public class PriceNotValidException extends RuntimeException{

	public PriceNotValidException() {
		super("Price cannot be null");
	}
}
