package com.example.demo.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.User;
import com.example.demo.dto.UserDto;

import com.example.demo.repositories.UserRepository;
import com.example.demo.services.exceptions.DataBaseException;
import com.example.demo.services.exceptions.ResourceNotFoundException;

import javax.persistence.EntityNotFoundException;

 

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	
	@Transactional(readOnly = true)
	public Page<UserDto> findAllPage(PageRequest pageRequest){
		Page<User> list = repository.findAll(pageRequest);
		
		return list.map(x -> new UserDto(x)) ;
	}
	
	
	public UserDto findById (Long id) {
		Optional<User> obj = repository.findById(id);
		//User entity = obj.get();
		
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found."));
		return new UserDto(entity);
	}
	
	@Transactional
	public UserDto insert(UserDto dto) {
		
		User entity = new User();
			entity.setName(dto.getName());
			entity.setAge(dto.getAge());
			entity.setEmail(dto.getEmail());
			entity = repository.save(entity);
		return new UserDto(entity);		
	}
	
	@Transactional
	public UserDto update(Long id, UserDto dto) {
		try {
			User entity  = repository.getReferenceById(id);
			entity.setName(dto.getName());
			entity.setAge(dto.getAge());
			entity.setEmail(dto.getEmail());
			entity = repository.save(entity);
			
			return new UserDto(entity);
			
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id Not found" + id);
		}
	}
	
	public void delete (Long id ) {
		try {
			repository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id Not found" + id);
		} catch(DataIntegrityViolationException e ) {
			throw new DataBaseException("Integrity Violation");
		}
	}
	
}
