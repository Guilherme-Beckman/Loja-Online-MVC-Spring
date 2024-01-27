package com.beckman.lojaonline.domain.order;

import java.util.List;



import com.beckman.lojaonline.domain.product.Product;
import com.beckman.lojaonline.domain.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
@Entity
public class Order {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@ManyToOne
private User user;
@OneToMany
private List<Product> products;

public Order(OrderDTO data) {
	this.id = data.id();
	this.products = data.products();
	this.user = data.user();
}

public Order(Long id, User user, List<Product> products) {
	this.id = id;
	this.user = user;
	this.products = products;
}

public Order() {

}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public List<Product> getProducts() {
	return products;
}

public void setProducts(List<Product> products) {
	this.products = products;
}



}
