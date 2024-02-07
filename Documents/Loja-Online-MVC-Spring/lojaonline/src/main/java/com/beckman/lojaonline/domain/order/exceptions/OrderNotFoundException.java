package com.beckman.lojaonline.domain.order.exceptions;

public class OrderNotFoundException extends RuntimeException {
	public OrderNotFoundException() {
		super("Order not found");
	}
}

