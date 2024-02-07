package com.beckman.lojaonline.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beckman.lojaonline.domain.product.ProductDTO;
import com.beckman.lojaonline.domain.user.UserDTO;
import com.beckman.lojaonline.domain.user.Users;
import com.beckman.lojaonline.services.UserService;
@RestController
@RequestMapping("/api/users")
public class UserController {
private UserService service;
	public UserController(UserService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Users> insert(@RequestBody UserDTO data) {
		Users newUser = this.service.insert(data);
		return ResponseEntity.ok().body(newUser);
	}
	@PutMapping("/{id}")
	public ResponseEntity<Users> update(@PathVariable("id") Long id,@RequestBody UserDTO data){
		Users user = this.service.update(id, data);
		return ResponseEntity.ok().body(user);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Users> delete(@PathVariable("id")Long id) {
		this.service.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping("/all")
	public ResponseEntity<List<Users>> getAll(){
		List<Users> listAll = this.service.getAll();
		return ResponseEntity.ok().body(listAll);
		}

	@GetMapping("/{id}")
	public ResponseEntity<Users> findById(@PathVariable("id") Long id){
		Users user = this.service.findById(id);
		return ResponseEntity.ok().body(user);
		}
	@PostMapping("/product/{id}")
	public ResponseEntity<Users> addProduct(@PathVariable("id") Long id,@RequestBody ProductDTO data){
		Users user = this.service.addProduct(id, data);
		return ResponseEntity.ok().body(user);
	}
}
