package com.beckman.lojaonline.domain.user;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.beckman.lojaonline.domain.cart.Cart;
import com.beckman.lojaonline.domain.product.Product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Users implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String password; 
	private UserRole role;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Cart cart;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Product> product;

	public Users(UserDTO data) {
		this.name = data.name();
		this.password = data.password();
		this.role = data.role();
	}
	public Users(RegisterDTO data) {
		this.name = data.name();
		this.password = data.password();
		this.role = data.role();
	}
	
	
	
	public Users(Long id, String name, String password, Cart cart, List<Product> products, UserRole role) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.cart = cart;
		this.product = products;
		this.role = role;
	}


	public Users() {
	}
	public Users(String name, String password, UserRole role) {
		this.name = name;
		this.password = password;
		this.role = role;
	}
public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
@Override
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


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		if (this.role== UserRole.ADMIN) {
			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		}else {
			return List.of(new SimpleGrantedAuthority("ROLE_USER"));
		}
	}


	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isEnabled() {
		
		return true;
	}


	public List<Product> getProduct() {
		return product;
	}


	public void setProduct(List<Product> product) {
		this.product = product;
	}


	public UserRole getRole() {
		return role;
	}


	public void setRole(UserRole role) {
		this.role = role;
	}


	
}
