package com.beckman.lojaonline.domain.user;

import java.util.List;

import com.beckman.lojaonline.domain.cart.Cart;
import com.beckman.lojaonline.domain.product.Product;

public record UserDTO(String name, String password, UserRole role) {
}
