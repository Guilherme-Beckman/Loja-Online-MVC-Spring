package com.beckman.lojaonline.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.beckman.lojaonline.domain.user.User;
import com.beckman.lojaonline.domain.user.UserDTO;
import com.beckman.lojaonline.domain.user.exceptions.UserNotFoundException;
import com.beckman.lojaonline.repositories.UserRepository;

@Service
public class UserService {
private UserRepository repository;
public User insert(UserDTO data){
	User newUser = new User(data);
    this.repository.save(newUser);
    return newUser;
}

public User update(Long id, UserDTO data) {
	User user = this.repository.findById(id).orElseThrow(UserNotFoundException::new);
	if(!(data.username().isEmpty())) {
		user.setUsername(data.username());
	};
	if(!(data.password().isEmpty())) {
		user.setPassword(data.password());
	};
	return user;	
}
public void delete(Long id) {
	User user = this.repository.findById(id).orElseThrow(UserNotFoundException::new);
this.repository.delete(user);
}
public List<User> getAll(){
	return repository.findAll();
}
public Optional<User> findById(Long id){
	return this.repository.findById(id);
}
}
