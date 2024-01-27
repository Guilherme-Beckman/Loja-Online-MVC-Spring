package com.beckman.lojaonline.domain.cart;

import java.util.List;

import com.beckman.lojaonline.domain.product.Product;
import com.beckman.lojaonline.domain.user.User;

public record CartDTO(Long id, List<Product> produtcs, User user) {

}
