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
import com.beckman.lojaonline.domain.user.Users;
import com.beckman.lojaonline.domain.user.exceptions.UserNotFoundException;
import com.beckman.lojaonline.repositories.CartItemRepository;
import com.beckman.lojaonline.repositories.CartRepository;
import com.beckman.lojaonline.repositories.ProductRepository;
import com.beckman.lojaonline.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CartService {
@Autowired
private CartRepository repository;
@Autowired
private CartItemRepository itemRepository ;
@Autowired
private ProductRepository productRepository ;
@Autowired
private UserRepository userRepository;

public List<CartItem>  getAllItensFromCart(Long userId){
	if (userId !=null && userId != 0) {
		Users user = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
		Cart cart = user.getCart();
		List<CartItem> allItens = cart.getItens();
		
		if(!allItens.isEmpty()) {
			return allItens;
		}else {
			  throw new ProductsListEmptyException();
		}
		
	}
	else {
		throw new IdNotValidException();
	}
	
}
@Transactional
public Cart deleteProductInCart(Long userId, Long productId){
	if (userId !=null && userId != 0 && productId !=null && productId != 0) {
		Users user = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
		Cart cart = user.getCart();
		var product = this.itemRepository.findById(this.makeItemUniqueId(userId, productId)).orElseThrow(ProductNotFoundException::new);	
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
public CartItem addItenOnCart (Long userId, Long productId) {
	if (userId !=null && userId != 0 && productId !=null && productId != 0) {
	Users user = this.userRepository.findById(userId).orElseThrow(CartNotFoundException::new);
	Cart cart = user.getCart();
	Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
	Long realId = this.makeItemUniqueId(userId, productId);
	var cartItem = this.itemRepository.findById(realId);	
	if(cartItem.isEmpty()) {
	CartItem item = new CartItem();
	item.setCart(cart);
	item.setId(productId);
	item.setName(product.getName());
	item.setPrice(product.getPrice());
	item.setDescription(product.getDescription());
	item.setRating(product.getRating());
	item.setQuantity(1);
	item.setRealId(realId);
	List<CartItem> itens = cart.getItens();
	itens.add(item);
	cart.setItens(itens);
	this.itemRepository.save(item);
	this.repository.save(cart);
	return item;

		}else {
		cartItem.get().setQuantity(cartItem.get().getQuantity()+1);
		this.itemRepository.save(cartItem.get());
		this.repository.save(cart);
		return cartItem.get();
	}
	}else throw new IdNotValidException();
}
public Long makeItemUniqueId(Long userId, Long productId) {
	Long cartId = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new).getCart().getId();

    String concatenatedId = String.valueOf(productId) + String.valueOf(cartId);
    Long realId= Long.parseLong(concatenatedId);
    return realId;
}
@Transactional
public CartItem decrementQuantityOfItens(Long userId, Long productId) {

	Users user = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
	Cart cart = user.getCart();
	CartItem cartItem = this.itemRepository.findById(this.makeItemUniqueId(userId, productId)).orElseThrow(ProductNotFoundException::new);

	List<CartItem> itens = cart.getItens();
	if(itens.contains(cartItem)) {
		cartItem.setQuantity(cartItem.getQuantity()-1);
		this.itemRepository.save(cartItem);
		this.repository.save(cart);
		if(cartItem.getQuantity()==0) {
			this.deleteProductInCart(userId, productId);
		}
		return cartItem;
	}else throw new EntityNotFoundException("The product don't exist in the cart") ;
	
}
}


