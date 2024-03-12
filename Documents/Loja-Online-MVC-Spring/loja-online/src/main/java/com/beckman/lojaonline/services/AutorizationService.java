package com.beckman.lojaonline.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.beckman.lojaonline.repositories.UserRepository;

@Service
public class AutorizationService implements UserDetailsService{
@Autowired
UserRepository repository;
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println("Tentativa de carregar usuário por nome: " + username);
    UserDetails user = repository.findByName(username);

    if (user == null) {
        System.out.println("Usuário não encontrado com o nome de usuário: " + username);
        throw new UsernameNotFoundException("Usuário não encontrado com o nome de usuário: " + username);
    }

    System.out.println("Usuário encontrado: " + user.getUsername());
    return user;
}

}
