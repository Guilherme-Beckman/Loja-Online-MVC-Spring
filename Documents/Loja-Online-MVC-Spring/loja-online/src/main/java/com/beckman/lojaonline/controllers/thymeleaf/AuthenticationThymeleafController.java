
package com.beckman.lojaonline.controllers.thymeleaf;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.beckman.lojaonline.controllers.AuthenticationService;
import com.beckman.lojaonline.domain.user.AuthenticationDTO;
import com.beckman.lojaonline.domain.user.RegisterDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;




@Controller

public class AuthenticationThymeleafController {
@Autowired
private AuthenticationService authenticationService;
@GetMapping("/login")
public String login(@ModelAttribute("user") AuthenticationDTO authenticationDTO, Model model,HttpServletResponse response) {

    model.addAttribute("user", authenticationDTO);
 var token = authenticationService.login(authenticationDTO);
 
  if (token !=null && token.equals("Preencha com suas credenciais")) {
	  model.addAttribute("error", "Preencha com suas credenciais");
      return "login";
  }
  else if (token !=null && !(token.equals("Preencha com suas credenciais"))) {
    	Cookie cookie = new Cookie("Authorization", "Bearer" + token);
        cookie.setPath("/");
        cookie.setMaxAge(7200); 
        response.addCookie(cookie);
        model.addAttribute("success}", "Logado com sucesso");
        return "redirect:home";
  }
  else {
        model.addAttribute("error", "Email ou Senha incorretos");
        return "login";
    }
    
}
@GetMapping ("/register") 
public String register(Model model, RegisterDTO registerDTO){
	model.addAttribute("user", registerDTO);
	return "register";
}
@PostMapping("/register")
public String registerSave(@ModelAttribute("user") RegisterDTO registerDTO, Model model, HttpServletResponse response) {
    if (authenticationService.register(registerDTO)==true) {

        model.addAttribute("success", "Registrado com sucesso, realize o login");
    } else {
        model.addAttribute("error", "Email já existe. Escolha outro.");
        return  "register";
    }
    return "login";
}

}

