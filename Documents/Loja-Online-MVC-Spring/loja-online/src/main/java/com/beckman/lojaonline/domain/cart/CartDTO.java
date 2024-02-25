package com.beckman.lojaonline.domain.cart;

import java.util.List;

import com.beckman.lojaonline.domain.cartitem.CartItem;
import com.beckman.lojaonline.domain.user.Users;

public record CartDTO( Users user, List<CartItem> itens) {
}