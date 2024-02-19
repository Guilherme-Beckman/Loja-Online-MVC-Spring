package com.beckman.lojaonline.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.beckman.lojaonline.domain.cart.Cart;
import com.beckman.lojaonline.domain.product.Product;
import com.beckman.lojaonline.domain.product.ProductDTO;
import com.beckman.lojaonline.domain.user.UserDTO;
import com.beckman.lojaonline.domain.user.Users;
import com.beckman.lojaonline.domain.user.exceptions.UserNotFoundException;
import com.beckman.lojaonline.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
private UserRepository repository;
public UserService(UserRepository repository) {
	this.repository = repository;
}

@Transactional
public Users insert(UserDTO data){
	   Users user = new Users(data);
    Cart shoppingCart = new Cart();
    shoppingCart.setUser(user);
    user.setCart(shoppingCart);
    repository.save(user);
    return user;
}


public Users update(Long id, UserDTO data) {
	Users user = this.repository.findById(id).orElseThrow(UserNotFoundException::new);
	if(!(data.name().isEmpty())) {
		user.setUsername(data.name());
	};
	if(!(data.password().isEmpty())) {
		user.setPassword(data.password());
	};
	this.repository.save(user);
	return user;	
}
public void delete(Long id) {
	Users user = this.repository.findById(id).orElseThrow(UserNotFoundException::new);
this.repository.delete(user);
}
public List<Users> getAll(){
	return this.repository.findAll();
}
public Users findById(Long id){
	Users user = this.repository.findById(id).orElseThrow(UserNotFoundException::new);
    return user;
}
@Transactional
public Users addProduct(Long id, ProductDTO data) {
  Users user = this.findById(id);
 Product newProduct = new Product(data);
 newProduct.setUser(user);
 List<Product> list = user.getProducts();
 list.add(newProduct);
 user.setProducts(list);
 repository.save(user);
 return user;
}
}
