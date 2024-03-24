package com.example.demo.services;

import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public Page<User> findAllPage(PageRequest pageRequest){
		
	}
	
}
