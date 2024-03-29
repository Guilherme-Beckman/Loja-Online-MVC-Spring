
	
	package com.beckman.lojaonline.services;

import static org.assertj.core.api.Assertions.assertThat;



import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.beckman.lojaonline.domain.cart.Cart;
import com.beckman.lojaonline.domain.cart.exceptions.CartNotFoundException;
import com.beckman.lojaonline.domain.cart.exceptions.IdNotValidException;
import com.beckman.lojaonline.domain.cart.exceptions.ProductsListEmptyException;
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
		Long cartId = this.userRepository.findById(userId).get().getCart().getId();
		Cart cart = this.repository.findById(cartId).get();
		this.addItenOnCart(userId, productId);
		
		Long itenId = this.makeItemUniqueId(userId, productId);
		Optional<CartItem> savedItem = this.itemRepository.findById(itenId);
		assertThat(savedItem).isNotEmpty();
		
		
		List<CartItem> itensOnCart = cart.getItens();
		assertThat(itensOnCart.contains(savedItem.get())).isTrue();
		

		
	}
	@Test
	@DisplayName("Should increment a quantity of a iten in a cart")
	void testIncrementQuantity() {
		RegisterDTO newRegister = new RegisterDTO("User", "aba8q9sin", UserRole.ADMIN);
		Long userId = this.insertUser(newRegister).getId();
		ProductDTO productDTO = new ProductDTO("A cool product", 12131, "It's really cool");
		Long productId = this.insertProduct(productDTO).getId();

		this.addItenOnCart(userId, productId);
		Long itenId = this.makeItemUniqueId(userId, productId);
		Optional<CartItem> savedItem = this.itemRepository.findById(itenId);
		assertThat(savedItem).isNotEmpty();
		
		Long cartId = this.userRepository.findById(userId).get().getCart().getId();
		Cart cart = this.repository.findById(cartId).get();
		List<CartItem> itensOnCart = cart.getItens();
		assertThat(itensOnCart.contains(savedItem.get())).isTrue();
		
		CartItem newIten = this.addItenOnCart(userId, productId);
		assertThat(newIten.getQuantity()==2).isTrue();
		assertThat(itensOnCart.contains(newIten)).isTrue();
		CartItem newIten2 = this.addItenOnCart(userId, productId);
		assertThat(newIten2.getQuantity()==3).isTrue(); 
	}
	@Test
	@DisplayName("Should decrement a quantity of a iten in a cart")
	void testDecrementQuantity() {
		RegisterDTO newRegister = new RegisterDTO("User", "aba8q9sin", UserRole.ADMIN);
		Long userId = this.insertUser(newRegister).getId();
		ProductDTO productDTO = new ProductDTO("A cool product", 12131, "It's really cool");
		Long productId = this.insertProduct(productDTO).getId();
		this.addItenOnCart(userId, productId);
		this.addItenOnCart(userId, productId);
		Long itenId = this.makeItemUniqueId(userId, productId);
		Optional<CartItem> savedItem = this.itemRepository.findById(itenId);
		assertThat(savedItem).isNotEmpty();
		Long cartId = this.userRepository.findById(userId).get().getCart().getId();
		Cart cart = this.repository.findById(cartId).get();
		List<CartItem> itensOnCart = cart.getItens();
		assertThat(itensOnCart.contains(savedItem.get())).isTrue();
		this.decrementQuantityOfItens(userId, productId);
		Optional<CartItem> addedItemA = this.itemRepository.findById(itenId);
		assertThat(addedItemA.get().getQuantity()).isEqualTo(1L);
		this.decrementQuantityOfItens(userId, productId);
		Optional<CartItem> addedItemB = this.itemRepository.findById(itenId);
		assertThat(addedItemB).isEmpty();
	   
	}
	@Test
	@DisplayName("Should delete an iten from a cart")
	void testDeleteItenCart() {
		
		RegisterDTO newRegister = new RegisterDTO("User", "aba8q9sin", UserRole.ADMIN);
		Long userId = this.insertUser(newRegister).getId();
		ProductDTO productDTO = new ProductDTO("A cool product", 12131, "It's really cool");
		Long productId = this.insertProduct(productDTO).getId();

		this.addItenOnCart(userId, productId);
		Long itenId = this.makeItemUniqueId(userId, productId);
		Optional<CartItem> savedItem = this.itemRepository.findById(itenId);
		assertThat(savedItem).isNotEmpty();
		
		Long cartId = this.userRepository.findById(userId).get().getCart().getId();
		Cart cart = this.repository.findById(cartId).get();
		List<CartItem> itensOnCart = cart.getItens();
		assertThat(itensOnCart.contains(savedItem.get())).isTrue();
		
		this.deleteProductInCart(userId, productId);
		Optional<CartItem> deletedItem= this.itemRepository.findById(itenId);
		assertThat(deletedItem.isEmpty()).isTrue();		
		
	}
	@Test
	@DisplayName("Should return list off all itens in cart")
	void testReturnListOfItens() {
		
		RegisterDTO newRegister = new RegisterDTO("User", "aba8q9sin", UserRole.ADMIN);
		Long userId = this.insertUser(newRegister).getId();
		ProductDTO productDTO = new ProductDTO("A cool product", 12131, "It's really cool");
		Long productId = this.insertProduct(productDTO).getId();
		ProductDTO productDTO2 = new ProductDTO("A cool product", 12131, "It's really cool");
		Long productId2 = this.insertProduct(productDTO2).getId();
		this.addItenOnCart(userId, productId);
		this.addItenOnCart(userId, productId2);

		
		Long itenId = this.makeItemUniqueId(userId, productId);
		Long itenId2 = this.makeItemUniqueId(userId, productId2);
		Optional<CartItem> savedItem = this.itemRepository.findById(itenId);
		Optional<CartItem> savedItem2= this.itemRepository.findById(itenId2);
		assertThat(savedItem).isNotEmpty();
		assertThat(savedItem2).isNotEmpty();
		
		Long cartId = this.userRepository.findById(userId).get().getCart().getId();
		Cart cart = this.repository.findById(cartId).get();
		List<CartItem> itensOnCart = cart.getItens();
		assertThat(itensOnCart.contains(savedItem.get())).isTrue();
		assertThat(itensOnCart.contains(savedItem2.get())).isTrue();
		
		List<CartItem> postItens = this.getAllItensFromCart(userId);
		postItens.forEach(itens->{
			assertThat(itensOnCart.contains(itens)).isTrue();
		});
				
		
	}
	
	

	
//---------------------------------------Services--------------------------------------//
	@Transactional
	public CartItem addItenOnCart (Long userId, Long productId) {
		if (userId !=null && userId != 0 && productId !=null && productId != 0) {
		Users user = userRepository.findById(userId).orElseThrow(CartNotFoundException::new);
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
public Long makeItemUniqueId(Long userId, Long productId) {
	Long cartId = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new).getCart().getId();

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
            this.userRepository.save(user);
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



