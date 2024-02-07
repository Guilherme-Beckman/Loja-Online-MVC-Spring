package com.beckman.lojaonline.domain.cart.exceptions;

public class ProductsListEmptyException extends RuntimeException{
public ProductsListEmptyException() {
	super("Product list is empty");
}
}
