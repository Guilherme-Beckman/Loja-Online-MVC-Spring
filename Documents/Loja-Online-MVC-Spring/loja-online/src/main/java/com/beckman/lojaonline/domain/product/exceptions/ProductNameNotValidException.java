package com.beckman.lojaonline.domain.product.exceptions;

public class ProductNameNotValidException extends RuntimeException{

	public ProductNameNotValidException() {
		super("Product name cannot be empty or blank");
	}
}
