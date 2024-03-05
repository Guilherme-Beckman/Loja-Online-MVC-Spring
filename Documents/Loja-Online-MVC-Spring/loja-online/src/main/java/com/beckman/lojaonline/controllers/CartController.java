package com.beckman.lojaonline.controllers;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beckman.lojaonline.domain.cart.Cart;
import com.beckman.lojaonline.domain.cartitem.CartItem;
import com.beckman.lojaonline.domain.product.Product;

import com.beckman.lojaonline.services.CartService;


@RestController
@RequestMapping("/api/cart")
public class CartController {
	@Autowired
    private CartService service;



@GetMapping("/products/{id}")
public ResponseEntity<List<CartItem>> getAllProducts(@PathVariable Long id){
	List<CartItem> listProduct = this.service.getAllItensFromCart(id);
	return ResponseEntity.ok().body(listProduct);
}
@PutMapping("/products/{id}/{productId}")
public ResponseEntity<Cart> addProductToCart(@PathVariable Long id,@PathVariable Long productId ){
	Cart cart= this.service.addItenOnCart(id,productId).getCart();
	return ResponseEntity.ok().body(cart);
}
@DeleteMapping("/products/{id}/{productId}")
public ResponseEntity<Cart> deleteProductInCart(@PathVariable Long id,@PathVariable Long productId ){
	Cart cart = this.service.deleteProductInCart(id,productId);
	return ResponseEntity.ok().body(cart);
}
}
