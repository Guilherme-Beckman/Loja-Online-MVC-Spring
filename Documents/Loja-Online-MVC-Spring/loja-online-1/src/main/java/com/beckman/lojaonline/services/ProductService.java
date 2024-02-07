package com.beckman.lojaonline.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.beckman.lojaonline.domain.order.exceptions.OrderNotFoundException;
import com.beckman.lojaonline.domain.product.Product;
import com.beckman.lojaonline.domain.product.ProductDTO;
import com.beckman.lojaonline.domain.product.exceptions.ProductNotFoundException;
import com.beckman.lojaonline.repositories.ProductRepository;

@Service
public class ProductService {
private ProductRepository repository;

public ProductService(ProductRepository repository) {
	this.repository = repository;
}

public Product insert(ProductDTO data){
	Product newProduct = new Product(data);
    this.repository.save(newProduct);
    return newProduct;
}

public Product update(Long id, ProductDTO data) {
	Product product = this.repository.findById(id).orElseThrow(ProductNotFoundException::new);
	if(!(data.name().isEmpty())) product.setName(data.name());
	if(!(data.price()==null)) product.setPrice(data.price());
	this.repository.save(product);
	return product;	
}
public void delete(Long id) {
	Product product = this.repository.findById(id).orElseThrow(ProductNotFoundException::new);
this.repository.delete(product);
}
public List<Product> getAll(){
	return repository.findAll();
}
public Optional<Product> findById(Long id){
	return this.repository.findById(id);
}

}
