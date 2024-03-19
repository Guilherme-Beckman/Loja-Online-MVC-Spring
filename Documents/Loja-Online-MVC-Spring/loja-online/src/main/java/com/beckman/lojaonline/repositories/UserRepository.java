package com.beckman.lojaonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.beckman.lojaonline.domain.user.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
	
UserDetails findByName(String name);

}
