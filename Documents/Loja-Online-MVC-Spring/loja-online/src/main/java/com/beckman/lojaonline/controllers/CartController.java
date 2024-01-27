package com.beckman.lojaonline.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beckman.lojaonline.domain.cart.Cart;
import com.beckman.lojaonline.domain.cart.CartDTO;
import com.beckman.lojaonline.services.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
private CartService service;

public CartController(CartService service) {
	this.service = service;
}

@PostMapping
public ResponseEntity<Cart> insert(@RequestBody CartDTO data) {
	Cart newCart = this.service.insert(data);
	return ResponseEntity.ok().body(newCart);
}
@PutMapping("/{id}")
public ResponseEntity<Cart> update(@PathVariable("id") Long id,@RequestBody CartDTO data){
	Cart cart = this.service.update(id, data);
	return ResponseEntity.ok().body(cart);
}
@DeleteMapping("/{id}")
public ResponseEntity<Cart> delete(@PathVariable("id")Long id) {
	this.service.delete(id);
	return ResponseEntity.noContent().build();
}
@GetMapping
public ResponseEntity<List<Cart>> getAll(){
	List<Cart> listAll = this.service.getAll();
	return ResponseEntity.ok().body(listAll);
	}

@GetMapping("/{id}")
public ResponseEntity<Optional<Cart>> findById(@PathVariable("id") Long id){
	Optional cart = this.service.findById(id);
	return ResponseEntity.ok().body(cart);
	}
}
