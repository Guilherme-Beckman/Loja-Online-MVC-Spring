package com.beckman.lojaonline.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beckman.lojaonline.domain.cart.Cart;
import com.beckman.lojaonline.domain.cart.exceptions.IdNotValidException;
import com.beckman.lojaonline.domain.cartitem.CartItem;
import com.beckman.lojaonline.domain.product.Product;
import com.beckman.lojaonline.domain.product.exceptions.ProductNotFoundException;
import com.beckman.lojaonline.repositories.CartItemRepository;

import jakarta.transaction.Transactional;

@Service
public class CartItemService {
@Autowired 
private CartItemRepository repository;
@Autowired
private ProductService productService;
@Autowired
private CartService cartService;
@Transactional
public CartItem addItenOnCart (Long cartId, Long productId) {
	if (cartId !=null && cartId != 0 && productId !=null && productId != 0) {
	Optional<CartItem> cartItem = this.repository.findById(productId);
	if(cartItem.isPresent() != true) {
	Cart cart = cartService.findById(cartId).orElseThrow(ProductNotFoundException::new);
	Product product = productService.findById(productId).orElseThrow(ProductNotFoundException::new);
	CartItem item = new CartItem();
	item.setCart(cart);
	item.setId(productId);
	item.setName(product.getName());
	item.setPrice(product.getPrice());
	item.setDescription(product.getDescription());
	item.setRating(product.getRating());
	item.setQuantity(1);
	List<CartItem> itens = cart.getItens();
	itens.add(item);
	cart.setItens(itens);
	return item;
	}else {
		cartItem.get().setQuantity(cartItem.get().getQuantity()+1);
		return cartItem.get();
	}
	}else throw new IdNotValidException();
}
public CartItem findById(Long id){
	CartItem cart = this.repository.findById(id).orElseThrow(ProductNotFoundException::new);
    return cart;
}

}