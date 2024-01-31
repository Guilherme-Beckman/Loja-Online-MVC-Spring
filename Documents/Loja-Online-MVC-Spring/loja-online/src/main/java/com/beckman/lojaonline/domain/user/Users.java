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
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cart_id")
	  @JsonManagedReference
	private Cart cart;
	  @JsonManagedReference
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Product> products;

	public Users(UserDTO data) {
		this.id = data.id();
		this.name = data.name();
		this.password = data.password();
		this.cart = data.cart();
		this.products = data.product();
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

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
		  
	}

	public List<Product> getProduct() {
		return products;
	}

	public void setProduct(List<Product> product) {
		this.products = product;
	}
	
	
}
