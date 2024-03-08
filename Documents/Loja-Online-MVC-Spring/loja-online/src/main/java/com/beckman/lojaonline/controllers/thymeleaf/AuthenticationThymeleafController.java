package com.beckman.lojaonline.controllers.thymeleaf;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beckman.lojaonline.controllers.AuthenticationController;
import com.beckman.lojaonline.domain.user.RegisterDTO;



@Controller

public class AuthenticationThymeleafController {
@Autowired
private AuthenticationController authenticationController;

@GetMapping ("/login") 
public String login(Model model, RegisterDTO registerDTO){
	model.addAttribute("user", registerDTO);
	return "login";
}
@GetMapping ("/register") 
public String register(Model model, RegisterDTO registerDTO){
	model.addAttribute("user", registerDTO);
	return "register";
}
@PostMapping("/register")
public String registerSave(@ModelAttribute("user")RegisterDTO registerDTO ){
	this.authenticationController.register(registerDTO);
	  return "redirect:/home?success=true";
}
}