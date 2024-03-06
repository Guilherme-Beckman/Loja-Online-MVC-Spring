package com.beckman.lojaonline.controllers.thymeleaf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.beckman.lojaonline.controllers.AuthenticationController;
import com.beckman.lojaonline.domain.user.RegisterDTO;



@Controller
@RequestMapping("/register-page")

public class RegisterPageController {

@GetMapping
public String home(Model model) {
	 model.addAttribute("registerDTO", new RegisterDTO(null, null, null));
	return ("register-page");
}



}