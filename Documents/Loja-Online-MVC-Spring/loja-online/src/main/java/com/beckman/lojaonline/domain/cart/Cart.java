package com.beckman.lojaonline.domain.cart;

import java.util.List;

import com.beckman.lojaonline.domain.product.Product;
import com.beckman.lojaonline.domain.user.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
@Entity
public class Cart {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@OneToMany
@JoinColumn(name = "products_id")
private List<Product> products;

private Long user_id;


public Cart(CartDTO data) {
	this.id = data.id();
	this.products = data.produtcs();
	this.user_id = data.id();
}


public Cart() {
	
}
public Cart(Long id, List<Product> productcs, Long userid) {
	this.id = id;
	this.products = productcs;
	this.user_id = userid;
}


public Long getId() {
	return id;
}


public void setId(Long id) {
	this.id = id;
}


public List<Product> getProductcs() {
	return products;
}


public void setProductcs(List<Product> productcs) {
	this.products = productcs;
}


public Long getUser() {
	return user_id;
}


public void setUser(Long userid) {
	this.user_id = userid;
}


}
