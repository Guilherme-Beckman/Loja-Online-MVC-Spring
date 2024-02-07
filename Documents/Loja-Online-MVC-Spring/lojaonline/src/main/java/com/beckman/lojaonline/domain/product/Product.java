package com.beckman.lojaonline.domain.product;

import com.beckman.lojaonline.domain.cart.Cart;
import com.beckman.lojaonline.domain.user.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
@Entity
public class Product {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private Integer price;
private String description;
@Column(name = "imagedata")
@Lob
private byte[] imageData;

@ManyToOne
@JoinColumn(name = "user_id")
@JsonIgnore
private Users user;

@ManyToOne
@JoinColumn(name = "cart_id")

@JsonIgnore
private Cart cart;

public Product(ProductDTO data) {
	this.id = data.id();
	this.name = data.name();
	this.price = data.price();
	this.description = data.description();
	this.user = data.user();

}

public Product(Long id, String name, Integer price, String description, byte[] imageData, Users user, Cart cart) {
	this.id = id;
	this.name = name;
	this.price = price;
	this.description = description;
	this.imageData = imageData;
	this.user = user;
	this.cart = cart;
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
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public byte[] getImageData() {
	return imageData;
}
public void setImageData(byte[] imageData) {
	this.imageData = imageData;
}


}