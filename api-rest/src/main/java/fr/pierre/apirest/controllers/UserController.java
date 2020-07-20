package fr.pierre.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.pierre.apirest.entities.User;
import fr.pierre.apirest.services.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public ResponseEntity<List<User>> getAll() {
		return ResponseEntity.ok(userService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable Long id) {
		User userById = userService.getById(id);
		if (userById != null) {
			return ResponseEntity.ok(userById);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/save")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		return ResponseEntity.ok(userService.save(user));
	}
	
	@PostMapping("/update/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
		User userById = userService.getById(id);
		if (userById != null && user.getId() == userById.getId()) {
			return ResponseEntity.ok(userService.save(userById));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
