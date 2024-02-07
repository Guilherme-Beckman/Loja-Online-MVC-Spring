package com.beckman.lojaonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beckman.lojaonline.domain.cart.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
}