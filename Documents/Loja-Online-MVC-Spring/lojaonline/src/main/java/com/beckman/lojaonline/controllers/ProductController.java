package com.beckman.lojaonline.controllers;
import org.springframework.http.HttpHeaders;

import org.springframework.http.MediaType;


import java.util.List;
import java.util.Optional;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beckman.lojaonline.domain.product.Product;
import com.beckman.lojaonline.domain.product.ProductDTO;
import com.beckman.lojaonline.services.ProductService;



@RestController
@RequestMapping("/api/product")
public class ProductController {

private ProductService service;

public ProductController (ProductService service) {
	this.service = service;
}
@GetMapping("/all")
public ResponseEntity< List<Product> > getAll(Model model) {
    List<Product> listAll = this.service.getAll();
    return ResponseEntity.ok().body(listAll);
}


@PutMapping("/{id}")
public ResponseEntity<Product> update(@PathVariable("id") Long id,@RequestBody ProductDTO data){
	Product product = this.service.update(id, data);
	return ResponseEntity.ok().body(product);
}
@DeleteMapping("/{id}")
public ResponseEntity<Product> delete(@PathVariable("id")Long id) {
	this.service.delete(id);
	return ResponseEntity.noContent().build();
}


@GetMapping("/{id}")
public ResponseEntity<Optional<Product>> findById(@PathVariable("id") Long id){
	Optional product = this.service.findById(id);
	return ResponseEntity.ok().body(product);
	}

}