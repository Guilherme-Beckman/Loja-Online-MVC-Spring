package com.beckman.lojaonline.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beckman.lojaonline.domain.cartitem.CartItem;
import com.beckman.lojaonline.domain.product.Product;
import com.beckman.lojaonline.domain.user.Users;
import com.beckman.lojaonline.domain.user.exceptions.UserNotFoundException;
import com.beckman.lojaonline.repositories.CartItemRepository;

@Service
public class CartItemService {
	@Autowired
	private CartItemRepository repository;
	public CartItem insert(Product data){
		CartItem cartItem = new CartItem(data);
	    repository.save(cartItem);
	    return cartItem;
	}
	public CartItem findById(Long id){
		CartItem user = this.repository.findById(id).orElseThrow(UserNotFoundException::new);
	    return user;
	}

}
