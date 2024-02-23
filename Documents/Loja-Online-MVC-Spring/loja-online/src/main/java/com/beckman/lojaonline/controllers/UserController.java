package com.beckman.lojaonline.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beckman.lojaonline.domain.product.Product;
import com.beckman.lojaonline.domain.product.ProductDTO;
import com.beckman.lojaonline.domain.user.Users;
import com.beckman.lojaonline.services.UserService;
@RestController
@RequestMapping("/api/users")
public class UserController {
private UserService service;
	public UserController(UserService service) {
		this.service = service;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Users> delete(@PathVariable Long id) {
		this.service.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping("/all")
	public ResponseEntity<List<Users>> getAll(){
		List<Users> listAll = this.service.getAll();
		return ResponseEntity.ok().body(listAll);
		}

	@GetMapping("/{id}")
	public ResponseEntity<Users> findById(@PathVariable Long id){
		Users user = this.service.findById(id);
		return ResponseEntity.ok().body(user);
		}
	@PostMapping("/product/{id}")
	public ResponseEntity addProduct(@PathVariable Long id,@RequestBody ProductDTO data){
		Product user = this.service.addProduct(id, data);
		return ResponseEntity.ok().build();
	}
}
