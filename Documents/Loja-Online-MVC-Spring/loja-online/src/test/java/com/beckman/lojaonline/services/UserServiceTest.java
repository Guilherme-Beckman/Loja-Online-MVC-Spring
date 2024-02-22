package com.beckman.lojaonline.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.beckman.lojaonline.domain.cart.Cart;
import com.beckman.lojaonline.domain.user.RegisterDTO;
import com.beckman.lojaonline.domain.user.UserDTO;
import com.beckman.lojaonline.domain.user.UserRole;
import com.beckman.lojaonline.domain.user.Users;
import com.beckman.lojaonline.domain.user.exceptions.UserNameNotValidException;
import com.beckman.lojaonline.repositories.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
@DataJpaTest
@ActiveProfiles("test")
class UserServiceTest {

@Autowired
EntityManager entityManager;
@Autowired
UserRepository repository;

	@Test
	@DisplayName("Should insert user with sucess on DB")
	void testInsertSucess() {
RegisterDTO newData = new RegisterDTO("user", "password1234", UserRole.ADMIN);
Users result = 	this.insert(newData);
assertThat(result).isNotNull();
assertThat(result.getId()).isNotNull();
assertThat(result.getCart()).isNotNull();

	}

	@Test
	@DisplayName("Should not insert user with sucess on DB")
	void testInsertNoSucess() {
		try {
RegisterDTO newData = new RegisterDTO( "", "password1234", UserRole.ADMIN);
Users result = 	this.insert(newData);
fail("Expected UserNameNotValidException, but no exception was thrown");
		}
		catch(UserNameNotValidException e) {
			assertThat(e).isNotNull();
			   assertThat(e.getMessage()).isEqualTo("Username is not valid");
		}

	}
	public Users insert(RegisterDTO data) {
		if(!(data.name().isEmpty() && data.name().isBlank())) {
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		   Users user = new Users(data);
		   user.setPassword(encryptedPassword);
	    Cart shoppingCart = new Cart();
	    shoppingCart.setUser(user);
	    user.setCart(shoppingCart);
	    repository.save(user);
	    return user;
	} else {
		throw new UserNameNotValidException();
	}
	}



}
