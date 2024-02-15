/*package com.beckman.lojaonline.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.beckman.lojaonline.domain.order.Order;
import com.beckman.lojaonline.domain.order.OrderDTO;
import com.beckman.lojaonline.domain.order.exceptions.OrderNotFoundException;
import com.beckman.lojaonline.repositories.OrderRepository;

@Service 
public class OrderService {
private OrderRepository repository;

public OrderService(OrderRepository repository) {
	this.repository = repository;
}

public Order insert(OrderDTO data){
	Order newOrder = new Order(data);
    this.repository.save(newOrder);
    return newOrder;
}

public Order update(Long id, OrderDTO data) {
	Order order = this.repository.findById(id).orElseThrow(OrderNotFoundException::new);
	if(!(data.()==null)) {
		order.setProducts(data.products());
	};
	this.repository.save(order);
	return order;	
}
public void delete(Long id) {
	Order order = this.repository.findById(id).orElseThrow(OrderNotFoundException::new);
this.repository.delete(order);
}
public List<Order> getAll(){
	return repository.findAll();
}
public Optional<Order> findById(Long id){
	return this.repository.findById(id);
}
}

*/
