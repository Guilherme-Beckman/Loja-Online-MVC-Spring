package com.beckman.lojaonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beckman.lojaonline.domain.cartitem.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	

}