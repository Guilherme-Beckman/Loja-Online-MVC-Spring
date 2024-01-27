package com.beckman.lojaonline.domain.cart.exceptions;

public class CartNotFoundException extends RuntimeException{
public CartNotFoundException() {
	super("Cart not found");
}
}
