package com.beckman.lojaonline.domain.product;

import com.beckman.lojaonline.domain.cart.Cart;
import com.beckman.lojaonline.domain.user.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class Product {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private Integer price;
@ManyToOne
@JoinColumn(name = "user_id")
private Users user;

@ManyToOne
@JoinColumn(name = "cart_id")
private Cart cart;

public Product(ProductDTO data) {
	this.id = data.id();
	this.name = data.name();
	this.price = data.price();
	this.user = data.user();
	this.cart = data.cart();
}
public Product(Long id, String name, Integer price) {
	this.id = id;
	this.name = name;
	this.price = price;
}
public Product() {
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Integer getPrice() {
	return price;
}
public void setPrice(Integer price) {
	this.price = price;
}
public Users getUser() {
	return user;
}
public void setUser(Users user) {
	this.user = user;
}
public Cart getCart() {
	return cart;
}
public void setCart(Cart cart) {
	this.cart = cart;
}


}
