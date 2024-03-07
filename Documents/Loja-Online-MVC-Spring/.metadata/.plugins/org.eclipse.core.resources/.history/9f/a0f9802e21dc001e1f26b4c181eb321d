package com.beckman.lojaonline.controllers.thymeleaf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beckman.lojaonline.domain.product.Product;
import com.beckman.lojaonline.services.ProductService;

@Controller
@RequestMapping("/home")
public class HomeThymeleafController {
	@Autowired
	private ProductService service;
@GetMapping
public String home(Model model) {
	List<Product> allProducts = this.service.getAll();
	 model.addAttribute("product", allProducts);
	return ("home");
}

}