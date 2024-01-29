package com.beckman.lojaonline.domain.cart;

import java.util.List;

import com.beckman.lojaonline.domain.product.Product;
import com.beckman.lojaonline.domain.user.Users;

import jakarta.persistence.CascadeType;
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
@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
private List<Product> products;

@OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
private Users user;

public Cart(CartDTO data) {
	this.id = data.id();
	this.products = data.produtcs();
	this.user = data.userid();
}


public Cart() {
	
}
public Cart(Long id, List<Product> productcs, Users userid) {
	this.id = id;
	this.products = productcs;
	this.user = userid;
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


public Users getUser() {
	return user;
}


public void setUser(Users userid) {
	this.user = userid;
}


}
