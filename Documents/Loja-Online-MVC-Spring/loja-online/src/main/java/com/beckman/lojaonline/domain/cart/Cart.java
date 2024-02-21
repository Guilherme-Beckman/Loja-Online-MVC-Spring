package com.beckman.lojaonline.domain.cart;

import java.util.List;

import com.beckman.lojaonline.domain.cartitem.CartItem;
import com.beckman.lojaonline.domain.user.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
@OneToOne
@JoinColumn(name = "user_id")
@JsonIgnore
private Users user;
@OneToMany(mappedBy = "cart")
private List<CartItem> itens;

public Cart(CartDTO data) {
	this.id = data.id();
	this.user = data.user();
	this.itens = data.itens();
}


public Cart() {
	
}

public Cart(Long id, Users user, List<CartItem> itens) {
	this.id = id;
	this.user = user;
	this.itens = itens;
}


public Long getId() {
	return id;
}


public void setId(Long id) {
	this.id = id;
}


public Users getUser() {
	return user;
}


public void setUser(Users user) {
	this.user = user;
}


public List<CartItem> getItens() {
	return itens;
}


public void setItens(List<CartItem> itens) {
	this.itens = itens;
}



}
