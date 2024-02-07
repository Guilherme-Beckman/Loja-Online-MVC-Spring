package com.beckman.lojaonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beckman.lojaonline.domain.order.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	

}
