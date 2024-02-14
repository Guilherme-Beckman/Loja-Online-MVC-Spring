package com.beckman.lojaonline.domain.user;

import java.util.List;

import com.beckman.lojaonline.domain.cart.Cart;
import com.beckman.lojaonline.domain.product.Product;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


@Entity
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String password; 
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Cart cart;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Product> product;

	public Users(UserDTO data) {
		this.id = data.id();
		this.name = data.name();
		this.password = data.password();
		this.cart = data.cart();
		this.product = data.products();
	}
	
	
	public Users(Long id, String name, String password, Cart cart, List<Product> products) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.cart = cart;
		this.product = products;
	}


	public Users() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return name;
	}

	public void setUsername(String username) {
		this.name = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Cart getCart() {
		return cart;
	}


	public void setCart(Cart cart) {
		this.cart = cart;
	}


	public List<Product> getProducts() {
		return product;
	}


	public void setProducts(List<Product> products) {
		this.product = products;
	}


	
}
