package com.beckman.lojaonline.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.beckman.lojaonline.domain.cart.Cart;
import com.beckman.lojaonline.domain.product.Product;
import com.beckman.lojaonline.domain.product.ProductDTO;
import com.beckman.lojaonline.domain.user.RegisterDTO;
import com.beckman.lojaonline.domain.user.Users;
import com.beckman.lojaonline.domain.user.exceptions.PasswordNotValidException;
import com.beckman.lojaonline.domain.user.exceptions.PasswordNotValidException;
import com.beckman.lojaonline.domain.user.exceptions.UserNameNotValidException;
import com.beckman.lojaonline.domain.user.exceptions.UserNotFoundException;
import com.beckman.lojaonline.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
private UserRepository repository;
public UserService(UserRepository repository) {
	this.repository = repository;
}
@Autowired
ProductService productService;

@Transactional
public Users insert(RegisterDTO data) {
	if(!(data.name().isEmpty() && data.name().isBlank())) {
		if((!(data.password().isEmpty() && data.password().isBlank()))){
	String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
	   Users user = new Users(data);
	   user.setPassword(encryptedPassword);
    Cart shoppingCart = new Cart();
    shoppingCart.setUser(user);
    user.setCart(shoppingCart);
    repository.save(user);
    return user;
		}else {
			throw new PasswordNotValidException();
		}
} else {
	throw new UserNameNotValidException();
}
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
public Product addProduct(Long id, ProductDTO data) {
    Users user = this.findById(id);
    Product newProduct = new Product(data);
    this.productService.insert(data);
    newProduct.setUser(user);
    if (user.getProducts()==null) {
    user.setProduct(new ArrayList<Product>());
    }
    List<Product> list = user.getProducts();
    list.add(newProduct);
    user.setProducts(list);
    repository.save(user);
    return newProduct;
}
}
