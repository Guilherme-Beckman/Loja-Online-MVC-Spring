package com.beckman.lojaonline.domain.cartitem;

import com.beckman.lojaonline.domain.cart.Cart;
import com.beckman.lojaonline.domain.product.Product;
import com.beckman.lojaonline.domain.product.ProductDTO;
import com.beckman.lojaonline.domain.user.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class CartItem {
@Id
private Long id;
private Integer quantity;
private String name;
private Integer price;
private String description;
private Long rating;
@ManyToOne
@JoinColumn(name = "cart_id")
@JsonIgnore
private Cart cart;
public CartItem() {
}

public CartItem(Long id, Cart cart, Integer quantity, String name, Integer price, String description, Users user, Long rating) {
	this.id = id;
	this.quantity = quantity;
	this.name = name;
	this.price = price;
	this.description = description;
	this.rating = rating;
}
public CartItem(Product product) {
	this.id = product.getId();
	this.quantity = 1;
	this.name = product.getName();
	this.price = product.getPrice();
	this.description = product.getDescription();

}

public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public Integer getQuantity() {
	return quantity;
}
public void setQuantity(Integer quantity) {
	this.quantity = quantity;
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

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public Long getRating() {
	return rating;
}

public void setRating(Long rating) {
	this.rating = rating;
}

public Cart getCart() {
	return cart;
}

public void setCart(Cart cart) {
	this.cart = cart;
}




}