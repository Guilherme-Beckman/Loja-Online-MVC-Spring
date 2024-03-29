package com.beckman.lojaonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beckman.lojaonline.domain.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	

}
