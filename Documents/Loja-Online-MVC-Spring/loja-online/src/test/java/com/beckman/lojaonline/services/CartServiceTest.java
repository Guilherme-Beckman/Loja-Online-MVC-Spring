package com.beckman.lojaonline.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.beckman.lojaonline.domain.cart.Cart;
import com.beckman.lojaonline.domain.cart.exceptions.CartNotFoundException;
import com.beckman.lojaonline.domain.cart.exceptions.IdNotValidException;
import com.beckman.lojaonline.domain.cartitem.CartItem;
import com.beckman.lojaonline.domain.product.Product;
import com.beckman.lojaonline.domain.product.exceptions.ProductNotFoundException;
import com.beckman.lojaonline.repositories.CartItemRepository;
import com.beckman.lojaonline.repositories.CartRepository;
import com.beckman.lojaonline.repositories.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
@DataJpaTest
@ActiveProfiles("test")
class CartServiceTest {
	@MockBean
	CartRepository repository;
	@Autowired
CartItemRepository itemRepository;
	@MockBean
	ProductRepository productRepository;
	@Test
	@DisplayName("Should insert a item in a cart")
	void testAddItenToCart() {
		
		Cart newCart = new Cart();
		Product productTest = new Product( "ProductTest", 122, "A cool product");
		productTest.setId(1L);
		when(productRepository.findById(1L)).thenReturn(Optional.of(productTest));
		when(repository.findById((1L))).thenReturn(Optional.of(newCart));
		this.addItenOnCart(1L, 1L);
		Optional<CartItem> addedItem = this.itemRepository.findById(1L);
		Optional<Cart> postCart = this.repository.findById(1L);
		boolean verifyIfItenWasAddedIntoCart = postCart.get().getItens().contains(addedItem.get());
		assertThat(verifyIfItenWasAddedIntoCart).isTrue();
		
		
	}
	@Test
	@DisplayName("Should increment a quantity of a iten in a cart")
	void testIncrementQuantity() {
		
		Cart newCart = new Cart();
		Product productTest = new Product( "ProductTest", 122, "A cool product");
		productTest.setId(1L);
		when(productRepository.findById(1L)).thenReturn(Optional.of(productTest));
		when(repository.findById((1L))).thenReturn(Optional.of(newCart));
		this.addItenOnCart(1L, 1L);
		Optional<CartItem> addedItem = this.itemRepository.findById(1L);
		assertThat(addedItem).isPresent();
		
		this.addItenOnCart(1L, 1L);
		Optional<CartItem> addedItem1 = this.itemRepository.findById(1L);
		assertThat(addedItem1.get().getQuantity()).isEqualTo(2);
	
		this.addItenOnCart(1L, 1L);
		Optional<CartItem> addedItem2 = this.itemRepository.findById(1L);
		assertThat(addedItem2.get().getQuantity()).isEqualTo(3);
	   
	}
	@Test
	@DisplayName("Should delete an iten from a cart")
	void testDeleteItenCart() {
		
		Cart newCart = new Cart();
		Product productTest = new Product( "ProductTest", 122, "A cool product");
		productTest.setId(1L);
		when(productRepository.findById(1L)).thenReturn(Optional.of(productTest));
		when(repository.findById((1L))).thenReturn(Optional.of(newCart));
		this.addItenOnCart(1L, 1L);
		Optional<CartItem> addedItem = this.itemRepository.findById(1L);
		assertThat(addedItem).isPresent();
		
		this.deleteProductInCart(1L, 1L);
		Optional<CartItem> deletedItem= this.itemRepository.findById(1L);
		assertThat(deletedItem.isEmpty()).isTrue();		
		
	}
	@Test
	@DisplayName("Should return a list of all products")
	void testgetAllProducts() {
		
		Cart newCart = new Cart();
		Product productTest1 = new Product("ProductTest1", 122, "A cool product 1");
		productTest1.setId(1L);
		Product productTest2 = new Product("ProductTest2", 150, "Another cool product 2");
		productTest2.setId(2L);
		when(productRepository.findById(1L)).thenReturn(Optional.of(productTest1));
		when(productRepository.findById(2L)).thenReturn(Optional.of(productTest2));
		when(repository.findById((1L))).thenReturn(Optional.of(newCart));
		this.addItenOnCart(1L, 1L);
		this.addItenOnCart(1L, 2L);
		Optional<CartItem> addedItem1 = this.itemRepository.findById(1L);
		Optional<CartItem> addedItem2 = this.itemRepository.findById(2L);
		assertThat(addedItem1).isPresent();
		assertThat(addedItem2).isPresent();
		
		List<CartItem> allItens = this.getAllItensFromCart(1L);
		assertThat(allItens.contains(addedItem1.get())).isTrue();
		assertThat(allItens.contains(addedItem2.get())).isTrue();		
	}
	
	
//---------------------------------------Services--------------------------------------//
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
public List<CartItem> getAllItensFromCart (Long cartId){
		Cart cart = this.repository.findById(cartId).orElseThrow(CartNotFoundException::new);
		List<CartItem> allItens = cart.getItens();
		return allItens;
		
	}
}