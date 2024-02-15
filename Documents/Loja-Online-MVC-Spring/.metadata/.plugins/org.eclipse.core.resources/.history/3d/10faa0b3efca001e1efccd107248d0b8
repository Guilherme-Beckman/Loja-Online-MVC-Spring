package com.beckman.lojaonline.services;

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
import com.beckman.lojaonline.repositories.CartRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {
private CartRepository repository;
@Autowired
private ProductService service ;

public CartService(CartRepository repository) {
   this.repository = repository;
}


public Cart update(Long id, CartDTO data) {
	Cart cart = this.repository.findById(id).orElseThrow(CartNotFoundException::new);
	if(!(data.itens()==null)) {
		cart.setItens(data.itens());
	};
	this.repository.save(cart);
	return cart;	
}
public void delete(Long id) {
	Cart cart = this.repository.findById(id).orElseThrow(CartNotFoundException::new);
this.repository.delete(cart);
}
public List<Cart> getAll(){
	return repository.findAll();
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
/*@Transactional
public Cart addProductToCart(Long id, Long productId){
	if (id !=null && id != 0 && productId !=null && productId != 0) {
			Cart cart = repository.findById(id).orElseThrow(CartNotFoundException::new);
			CartItem product = service.findById(productId).orElseThrow(ProductNotFoundException::new);
			List<CartItem> allProducts = cart.getItens();
			allProducts.add(product);
			cart.getItens(allProducts);
			this.repository.save(cart);
			product.setCart(cart);
			return cart;
		}else {
			throw new IdNotValidException();
		}
		
	}*/
@Transactional
public Cart deleteProductInCart(Long id, Long productId){
	if (id !=null && id != 0 && productId !=null && productId != 0) {
		Cart cart = repository.findById(id).orElseThrow(CartNotFoundException::new);
		Product product = service.findById(productId).orElseThrow(ProductNotFoundException::new);
		List<CartItem> allProducts = cart.getItens();
	       if (allProducts.contains(product)) {
	            allProducts.remove(product);
	            cart.setItens(allProducts);
	            repository.save(cart);
	            return cart;
	        } else {
	            throw new ProductNotFoundException();
	        }
	       }else {
		throw new IdNotValidException();
	}
}
}

