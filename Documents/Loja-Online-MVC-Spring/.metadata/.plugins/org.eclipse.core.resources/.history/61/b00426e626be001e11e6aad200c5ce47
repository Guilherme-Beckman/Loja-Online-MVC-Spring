package com.beckman.lojaonline.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.beckman.lojaonline.domain.cart.Cart;
import com.beckman.lojaonline.domain.cart.CartDTO;
import com.beckman.lojaonline.domain.cart.exceptions.CartNotFoundException;
import com.beckman.lojaonline.repositories.CartRepository;

@Service
public class CartService {
private CartRepository repository;

public CartService(CartRepository repository) {
   this.repository = repository;
}

public Cart insert(CartDTO data){
	Cart newCart = new Cart(data);
    this.repository.save(newCart);
    return newCart;
}

public Cart update(Long id, CartDTO data) {
	Cart cart = this.repository.findById(id).orElseThrow(CartNotFoundException::new);
	if(!(data.produtcs()==null)) {
		cart.setProductcs(data.produtcs());
	};
	return cart;	
}
public void delete(Long id) {
	Cart cart = this.repository.findById(id).orElseThrow(CartNotFoundException::new);
this.repository.delete(cart);
}
public List<Cart> getAll(){
	return repository.findAll();
}
public Optional<Cart> findById(Long id){
	return this.repository.findById(id);
}
}
