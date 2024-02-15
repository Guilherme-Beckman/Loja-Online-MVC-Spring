package com.beckman.lojaonline.domain.product;

import com.beckman.lojaonline.domain.cart.Cart;
import com.beckman.lojaonline.domain.user.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
private String description;
private Long rating;
@ManyToOne
@JoinColumn(name = "user_id")
@JsonIgnore
private Users user;


public Product(ProductDTO data) {
	this.id = data.id();
	this.name = data.name();
	this.price = data.price();
	this.description = data.description();


}
public Product(Long id, String name, Integer price) {
	this.id = id;
	this.name = name;
	this.price = price;
	this.description = description;
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


public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}


}
