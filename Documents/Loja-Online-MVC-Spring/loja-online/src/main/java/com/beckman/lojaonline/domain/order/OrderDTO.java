package com.beckman.lojaonline.domain.order;

import java.util.List;

import com.beckman.lojaonline.domain.product.Product;
import com.beckman.lojaonline.domain.user.User;

public record OrderDTO(Long id, User user, List<Product> products) {

}
