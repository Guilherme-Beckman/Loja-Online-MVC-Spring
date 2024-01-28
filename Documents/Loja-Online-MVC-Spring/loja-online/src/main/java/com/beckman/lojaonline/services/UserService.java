package com.beckman.lojaonline.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.beckman.lojaonline.domain.user.UserDTO;
import com.beckman.lojaonline.domain.user.Users;
import com.beckman.lojaonline.domain.user.exceptions.UserNotFoundException;
import com.beckman.lojaonline.repositories.UserRepository;

@Service
public class UserService {
private UserRepository repository;
public UserService(UserRepository repository) {
	this.repository = repository;
}


public Users insert(UserDTO data){
	Users newUser = new Users(data);
    this.repository.save(newUser);
    return newUser;
}

public Users update(Long id, UserDTO data) {
	Users Users = this.repository.findById(id).orElseThrow(UserNotFoundException::new);
	if(!(data.name().isEmpty())) {
		Users.setUsername(data.name());
	};
	if(!(data.password().isEmpty())) {
		Users.setPassword(data.password());
	};
	this.repository.save(Users);
	return Users;	
}
public void delete(Long id) {
	Users Users = this.repository.findById(id).orElseThrow(UserNotFoundException::new);
this.repository.delete(Users);
}
public List<Users> getAll(){
	return repository.findAll();
}
public Optional<Users> findById(Long id){
	return this.repository.findById(id);
}
}
