package com.beckman.lojaonline.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beckman.lojaonline.domain.cart.Cart;
import com.beckman.lojaonline.domain.cart.CartDTO;
import com.beckman.lojaonline.domain.cart.exceptions.CartNotFoundException;
import com.beckman.lojaonline.domain.cart.exceptions.IdNotValidException;
import com.beckman.lojaonline.domain.cart.exceptions.ProductsListEmptyException;
import com.beckman.lojaonline.domain.cartitem.CartItem;
import com.beckman.lojaonline.domain.product.Product;
import com.beckman.lojaonline.domain.product.exceptions.ProductNotFoundException;
import com.beckman.lojaonline.repositories.CartItemRepository;
import com.beckman.lojaonline.repositories.CartRepository;
import com.beckman.lojaonline.repositories.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CartService {
private CartRepository repository;
@Autowired
private CartItemRepository itemRepository ;
private ProductRepository productRepository ;
public CartService(CartRepository repository) {
   this.repository = repository;
}



public List<CartItem> getAllItensFromCart (Long cartId){
	Cart cart = this.repository.findById(cartId).orElseThrow(CartNotFoundException::new);
	List<CartItem> allItens = cart.getItens();
	return allItens;
	
}
public Optional<Cart> findById(Long id){
	return this.repository.findById(id);
}
@Transactional
public List<CartItem>  getAllProducts(Long id){
	if (id !=null && id != 0) {
		Cart cart = repository.findById(id).orElseThrow(CartNotFoundException::new);
		List<CartItem> allProducts = cart.getItens();
		if(!allProducts.isEmpty()) {
			return allProducts;
		}else {
			  throw new ProductsListEmptyException();
		}
		
	}
	else {
		throw new IdNotValidException();
	}
	
}

@Transactional
public Cart deleteProductInCart(Long id, Long productId){
	if (id !=null && id != 0 && productId !=null && productId != 0) {
		Cart cart = this.repository.findById(id).orElseThrow(CartNotFoundException::new);
		CartItem product = this.itemRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
		List<CartItem> allProducts = cart.getItens();
	       if (allProducts.contains(product)) {
	            allProducts.remove(product);
	            cart.setItens(allProducts);
	            repository.save(cart);
	            this.itemRepository.delete(product);
	            return cart;
	        } else {
	            throw new EntityNotFoundException("Product is not on the list");
	        }
	       }else {
		throw new IdNotValidException();
	}
	}
@Transactional
public CartItem addItenOnCart (Long cartId, Long productId) {
	if (cartId !=null && cartId != 0 && productId !=null && productId != 0) {
	var cartItem = this.itemRepository.findById(productId);
	if(cartItem.isEmpty()) {
	Cart cart = repository.findById(cartId).orElseThrow(CartNotFoundException::new);
	Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
	CartItem item = new CartItem();
	item.setCart(cart);
	item.setId(productId);
	item.setName(product.getName());
	item.setPrice(product.getPrice());
	item.setDescription(product.getDescription());
	item.setRating(product.getRating());
	item.setQuantity(1);
	if(cart.getItens().isEmpty()) {
	List<CartItem> itens = new LinkedList<>();
	itens.add(item);
	cart.setItens(itens);
	this.repository.save(cart);
	this.itemRepository.save(item);
	return item;
	}else {
		cart.getItens().add(item);
		this.repository.save(cart);
		this.itemRepository.save(item);
		return item;
		}
	}else {
		cartItem.get().setQuantity(cartItem.get().getQuantity()+1);
		return cartItem.get();
	}
	}else throw new IdNotValidException();
}
}


