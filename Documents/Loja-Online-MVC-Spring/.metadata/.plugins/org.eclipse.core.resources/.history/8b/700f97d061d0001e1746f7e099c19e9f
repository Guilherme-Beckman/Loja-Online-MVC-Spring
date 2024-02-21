package com.beckman.lojaonline.domain.order;

import java.util.List;

import com.beckman.lojaonline.domain.product.Product;
import com.beckman.lojaonline.domain.user.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
@Entity
public class Orders {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

public Orders(OrderDTO data) {
	this.id = data.id();
}

public Orders(Long id, Users user, List<Product> products) {
	this.id = id;
}

public Orders() {

}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}




}
