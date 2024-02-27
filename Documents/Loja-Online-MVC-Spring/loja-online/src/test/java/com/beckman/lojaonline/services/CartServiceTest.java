package com.beckman.lojaonline.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.beckman.lojaonline.domain.cart.Cart;
import com.beckman.lojaonline.domain.cart.exceptions.CartNotFoundException;
import com.beckman.lojaonline.domain.cart.exceptions.IdNotValidException;
import com.beckman.lojaonline.domain.cartitem.CartItem;
import com.beckman.lojaonline.domain.product.Product;
import com.beckman.lojaonline.domain.product.ProductDTO;
import com.beckman.lojaonline.domain.product.exceptions.PriceNotValidException;
import com.beckman.lojaonline.domain.product.exceptions.ProductNameNotValidException;
import com.beckman.lojaonline.domain.product.exceptions.ProductNotFoundException;
import com.beckman.lojaonline.domain.user.RegisterDTO;
import com.beckman.lojaonline.domain.user.UserRole;
import com.beckman.lojaonline.domain.user.Users;
import com.beckman.lojaonline.domain.user.exceptions.PasswordNotValidException;
import com.beckman.lojaonline.domain.user.exceptions.UserNameNotValidException;
import com.beckman.lojaonline.domain.user.exceptions.UserNotFoundException;
import com.beckman.lojaonline.repositories.CartItemRepository;
import com.beckman.lojaonline.repositories.CartRepository;
import com.beckman.lojaonline.repositories.ProductRepository;
import com.beckman.lojaonline.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
@DataJpaTest
@ActiveProfiles("test")
class CartServiceTest {
	@Autowired
	private CartRepository repository;
	@Autowired
	private CartItemRepository itemRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	@Test
	@DisplayName("Should insert a item in a cart")
	void testAddItenToCart() {
		RegisterDTO newRegister = new RegisterDTO("User", "aba8q9sin", UserRole.ADMIN);
		Long userId = this.insertUser(newRegister).getId();
		ProductDTO productDTO = new ProductDTO("A cool product", 12131, "It's really cool");
		Long productId = this.insertProduct(productDTO).getId();

		this.addItenOnCart(userId, productId);
		Long itenId = this.makeItemUniqueId(userId, productId);
		Optional<CartItem> savedItem = this.itemRepository.findById(itenId);
		assertThat(savedItem).isNotEmpty();
		
		Cart cart = this.userRepository.findById(userId).get().getCart();
		List<CartItem> itensOnCart = cart.getItens();
		assertThat(itensOnCart.contains(savedItem.get())).isTrue();
		
		CartItem newIten = this.addItenOnCart(userId, productId);
		assertThat(newIten.getQuantity()==2).isTrue();
		assertThat(itensOnCart.contains(newIten)).isTrue();
		CartItem newIten2 = this.addItenOnCart(userId, productId);
		assertThat(newIten2.getQuantity()==3).isTrue();
		
	}

	
//---------------------------------------Services--------------------------------------//
	@Transactional
	public CartItem addItenOnCart (Long userId, Long productId) {
		if (userId !=null && userId != 0 && productId !=null && productId != 0) {
		Users user = userRepository.findById(userId).orElseThrow(CartNotFoundException::new);
		Cart cart = user.getCart();
		Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
		var cartItem = this.itemRepository.findById(this.makeItemUniqueId(productId, cart.getId()));	
		System.out.println("Se chegou aqui vai flamengo");
		if(cartItem.isEmpty()) {
		CartItem item = new CartItem();
		item.setCart(cart);
		item.setId(productId);
		item.setName(product.getName());
		item.setPrice(product.getPrice());
		item.setDescription(product.getDescription());
		item.setRating(product.getRating());
		item.setQuantity(1);
		System.out.println("Se chegou aqui vai flamengo");
		item.setRealId(this.makeItemUniqueId(productId, cart.getId()));
		System.out.println("Se chegou aqui vai flamengo");
		List<CartItem> itens = new LinkedList<>();
		itens.add(item);
		cart.setItens(itens);
		System.out.println("Se chegou aqui vai flamengo");
		
		System.out.println("Se chegou aqui vai flamengo");
		CartItem savedItem = this.itemRepository.save(item);
		System.out.println("Id:"+savedItem.getId()+"Name:"+ savedItem.getName());
		this.repository.save(cart);
		System.out.println("Se chegou aqui vai flamengo");
		cart.getItens().forEach(aux ->{
			System.out.println("realId: "+aux.getRealId()+"\n");
			System.out.println("Id: "+aux.getId()+"\n");
			System.out.println("Name: "+aux.getName()+"\n");
			System.out.println("Price: "+aux.getPrice()+"\n");
		});
		return item;

			}else {
			cartItem.get().setQuantity(cartItem.get().getQuantity()+1);
			this.itemRepository.save(cartItem.get());
			this.repository.save(cart);
			System.out.println("Se chegou aqui vai flamengo");
			cart.getItens().forEach(aux ->{
				System.out.println("realId: "+aux.getRealId()+"\n");
				System.out.println("Id: "+aux.getId()+"\n");
				System.out.println("Name: "+aux.getName()+"\n");
				System.out.println("Price: "+aux.getPrice()+"\n");
				System.out.println("Quantity: "+aux.getQuantity()+"\n");
			});
			return cartItem.get();
		}
		}else throw new IdNotValidException();
	}
	@Transactional
	public Cart deleteProductInCart(Long userId, Long productId){
		if (userId !=null && userId != 0 && productId !=null && productId != 0) {
			Users user = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
			Cart cart = user.getCart();
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
public List<CartItem> getAllItensFromCart (Long userId){
		Users user = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
		Cart cart = user.getCart();
		List<CartItem> allItens = cart.getItens();
		return allItens;
		
	}
@Transactional
public CartItem decrementQuantityOfItens(Long userId, Long cartItemId) {
	Users user = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
	CartItem cartItem = this.itemRepository.findById(cartItemId).orElseThrow(ProductNotFoundException::new);
	Cart cart = user.getCart();
	List<CartItem> itens = cart.getItens();
	if(itens.contains(cartItem)) {
		cartItem.setQuantity(cartItem.getQuantity()-1);
		this.itemRepository.save(cartItem);
		this.repository.save(cart);
		if(cartItem.getQuantity()==0) {
			this.deleteProductInCart(userId, cartItemId);
		}
		return cartItem;
	}else throw new EntityNotFoundException("The product don't exist in the cart") ;
	
}
public Long makeItemUniqueId(Long productId, Long cartId) {
    String concatenatedId = String.valueOf(productId) + String.valueOf(cartId);
    Long realId= Long.parseLong(concatenatedId);
    return realId;
}
public Users insertUser(RegisterDTO data) {
    if (!(data.name().isEmpty() && data.name().isBlank())) {
        if (!(data.password().isEmpty() && data.password().isBlank())) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
            Users user = new Users(data);
            user.setPassword(encryptedPassword);
            Cart shoppingCart = new Cart();
            shoppingCart.setUser(user);
            user.setCart(shoppingCart);
            userRepository.save(user);
            return user;
        } else {
            throw new PasswordNotValidException();
        }
    } else {
        throw new UserNameNotValidException();
    }
}
public Product insertProduct(ProductDTO data) {
    if (!(data.name().isEmpty() && data.name().isBlank())) {
        if (!(data.price()==null)){
            Product product = new Product(data);
            this.productRepository.save(product);
            return product;
        } else {
            throw new PriceNotValidException();
        }
    } else {
        throw new ProductNameNotValidException();
    }
}
}
