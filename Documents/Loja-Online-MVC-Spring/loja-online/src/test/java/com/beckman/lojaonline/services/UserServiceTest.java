package com.beckman.lojaonline.services;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.beckman.lojaonline.domain.cart.Cart;
import com.beckman.lojaonline.domain.product.Product;
import com.beckman.lojaonline.domain.product.ProductDTO;
import com.beckman.lojaonline.domain.product.exceptions.PriceNotValidException;
import com.beckman.lojaonline.domain.product.exceptions.ProductNameNotValidException;
import com.beckman.lojaonline.domain.user.RegisterDTO;
import com.beckman.lojaonline.domain.user.UserRole;
import com.beckman.lojaonline.domain.user.Users;
import com.beckman.lojaonline.domain.user.exceptions.PasswordNotValidException;
import com.beckman.lojaonline.domain.user.exceptions.UserNameNotValidException;
import com.beckman.lojaonline.domain.user.exceptions.UserNotFoundException;
import com.beckman.lojaonline.repositories.ProductRepository;
import com.beckman.lojaonline.repositories.UserRepository;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository repository;
    

    @Autowired
    ProductRepository productRepository;
    
   

    @Test
    @DisplayName("Should insert user with success on DB")
    void testInsertSuccess() {
        RegisterDTO newData = new RegisterDTO("user", "password1234", UserRole.ADMIN);
        Users result = this.insert(newData);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getCart()).isNotNull();
    }

    @Test
    @DisplayName("Should not insert user with success on DB, cause username is blank")
    void testInsertNoSuccessCase1() {
        try {
            RegisterDTO newData = new RegisterDTO("", "password1234", UserRole.ADMIN);
            Users result = this.insert(newData);
            fail("Expected UserNameNotValidException, but no exception was thrown");
        } catch (UserNameNotValidException e) {
            assertThat(e).isNotNull();
            assertThat(e.getMessage()).isEqualTo("Username cannot be empty or blank");
        }
    }

    @Test
    @DisplayName("Should not insert user with success on DB, cause password is blank")
    void testInsertNoSuccessCase2() {
        try {
            RegisterDTO newData = new RegisterDTO("name", "", UserRole.ADMIN);
            Users result = this.insert(newData);
            fail("Expected PasswordNotValidException, but no exception was thrown");
        } catch (PasswordNotValidException e) {
            assertThat(e).isNotNull();
            assertThat(e.getMessage()).isEqualTo("Password cannot be empty or blank");
        }
    }

    @Test
    @DisplayName("Should delete user with success")
    void testDeleteSuccess() {
        RegisterDTO newData = new RegisterDTO("user1", "password1234", UserRole.ADMIN);
        Users result = this.insert(newData);
        this.delete(result.getId());
        Optional<Users> deletedUser = this.repository.findById(result.getId());
        assertThat(deletedUser).isNotPresent();
    }

    @Test
    @DisplayName("Should add a new product, and associate this product to a user")
    void testAddNewProductSuccess() {
        ProductDTO newProductDTO = new ProductDTO("Product", 4000, "A cool product");
        RegisterDTO newData = new RegisterDTO("user2", "password1234", UserRole.ADMIN);
        Users newUser = this.insert(newData);
        Long productId = this.addProduct(newUser.getId(), newProductDTO).getId();
        Optional<Product> testProduct = this.productRepository.findById(productId);
        assertThat(testProduct).isPresent();
        assertThat(testProduct.get().getUser().getId()).isEqualTo(newUser.getId());
    }


    
    //-------------------------------------Services------------------------------------------------------//
    public Users insert(RegisterDTO data) {
        if (!(data.name().isEmpty() && data.name().isBlank())) {
            if (!(data.password().isEmpty() && data.password().isBlank())) {
                String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
                Users user = new Users(data);
                user.setPassword(encryptedPassword);
                Cart shoppingCart = new Cart();
                shoppingCart.setUser(user);
                user.setCart(shoppingCart);
                repository.save(user);
                return user;
            } else {
                throw new PasswordNotValidException();
            }
        } else {
            throw new UserNameNotValidException();
        }
    }

    public void delete(Long id) {
        Users user = this.repository.findById(id).orElseThrow(UserNotFoundException::new);
        this.repository.delete(user);
    }

    public Product addProduct(Long id, ProductDTO data) {
        Users user = this.findById(id);
        Product newProduct = new Product(data);
        this.insert(data);
        newProduct.setUser(user);
        if (user.getProducts()==null) {
        user.setProduct(new ArrayList<Product>());
        }
        List<Product> list = user.getProducts();
        list.add(newProduct);
        user.setProducts(list);
        repository.save(user);
        return newProduct;
    }

    public Users findById(Long id) {
        Users user = this.repository.findById(id).orElseThrow(UserNotFoundException::new);
        return user;
    }
    public Product insert(ProductDTO data) {
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
