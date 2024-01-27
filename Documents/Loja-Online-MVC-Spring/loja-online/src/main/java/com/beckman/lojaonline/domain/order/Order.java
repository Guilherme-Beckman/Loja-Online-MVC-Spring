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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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


}