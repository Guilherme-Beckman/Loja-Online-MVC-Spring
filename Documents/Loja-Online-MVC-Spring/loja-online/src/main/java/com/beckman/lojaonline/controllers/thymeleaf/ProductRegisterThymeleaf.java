package com.beckman.lojaonline.controllers.thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beckman.lojaonline.domain.product.ProductDTO;

import com.beckman.lojaonline.domain.user.Users;
import com.beckman.lojaonline.infra.security.SecurityFilter;
import com.beckman.lojaonline.infra.security.TokenService;
import com.beckman.lojaonline.repositories.UserRepository;
import com.beckman.lojaonline.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/products")
public class ProductRegisterThymeleaf {
@Autowired
private UserService userService;
@Autowired 
SecurityFilter security;
@Autowired
TokenService tokenService;
@Autowired
UserRepository repository;

@GetMapping
public String showProductForm(Model model, ProductDTO productDTO) {
	model.addAttribute("product", productDTO);
		return "productRegister";
}
@PostMapping
public String registerProduct(@ModelAttribute("product") ProductDTO productDTO, Model model, HttpServletRequest request) {
    System.out.println("aaaaaaaaaaaa");
	var token = this.security.recoverToken(request);
	System.out.println("Esse é token dentro do metodo register:"+token);
    if(token != null) {
        var name = tokenService.validateToken(token);
        System.out.println("Nome:"+name);
        var userDetails = repository.findByName(name);
        
        System.out.println("aaaaaaaaaaaa");
      
        return "redirect:home";
    }else {
    	return "redirect:home";
    }
}

}
