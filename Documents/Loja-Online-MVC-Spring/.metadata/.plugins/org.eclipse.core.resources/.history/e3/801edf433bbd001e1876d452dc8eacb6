package com.beckman.lojaonline.domain.user;

import java.util.List;

import com.beckman.lojaonline.domain.order.OrderDTO;
import com.beckman.lojaonline.domain.product.Product;

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
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;

	public User(UserDTO data) {
		this.id = data.id();
		this.username = data.username();
		this.password = data.password();
	}
}
