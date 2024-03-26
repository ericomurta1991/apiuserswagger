package com.example.demo.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.dto.UserDto;
import com.example.demo.services.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@GetMapping(produces = "application/json")
	@ApiOperation(value = "search all users", notes = "searches all users and displays them in a page format")
	public ResponseEntity<Page<UserDto>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy
			
			){
		
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		
			Page<UserDto> list = service.findAllPage(pageRequest);
		
			return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	@ApiOperation(value = "search user by id", notes = "method used to search for a specific user by id")
	@ApiResponses(
			{
				@ApiResponse(code = 401, message = "Unauthorized"),
				@ApiResponse(code = 403, message = "Forbidden"),
				@ApiResponse(code = 404, message = "Not Found")
			})
	public ResponseEntity<UserDto> findById(@PathVariable Long id){
		UserDto dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	
	@PostMapping(produces = "application/json")
	public ResponseEntity<UserDto> insert(@RequestBody UserDto dto){
		dto = service.insert(dto);
		URI uri =  ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto dto){
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete (@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
