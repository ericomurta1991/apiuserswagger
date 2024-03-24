package com.example.demo.dto;

import java.io.Serializable;

import com.example.demo.entities.User;

public class UserDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private Integer age;
	private String email;
	
	public UserDto() {}

	public UserDto(Long id, String name, Integer age, String email) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.email = email;
	}
	
	public UserDto(User entity){
		super();
		this.id = entity.getId();
		this.name = entity.getName();
		this.age = entity.getAge();
		this.email = entity.getEmail();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
	
	
	
	
	
}