package com.asusoftware.ecommerce.controllers;

import java.util.List;

import com.asusoftware.ecommerce.dto.LoginDto;
import com.asusoftware.ecommerce.dto.UserDto;
import com.asusoftware.ecommerce.exceptions.InvalidPasswordException;
import com.asusoftware.ecommerce.exceptions.NotFoundUserException;
import com.asusoftware.ecommerce.model.User;
import com.asusoftware.ecommerce.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*") // Abilitazione delle CORS - Cross Origin Resource Sharing
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	// add user
	// @PostMapping(value = "create",consumes = "application/json", produces = "application/json")
	@PostMapping
	private ResponseEntity createUser(@RequestBody User user) {
		try {
			userService.insertUser(user);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception ex) {
			return ResponseEntity.badRequest().build();
		}
	}

	// login
	@PostMapping("login")
	private ResponseEntity<UserDto> findByEmailAndPassword(@RequestBody LoginDto login) {
		try {
			UserDto user = userService.findByEmailAndPassword(login.getEmail(), login.getPassword());
			return ResponseEntity.ok(user);
		} catch (NotFoundUserException ex) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	// get all users
	@GetMapping
	private ResponseEntity<List<UserDto>> getUsers() {
		try {
			return ResponseEntity.ok(userService.getUsers());
		} catch (Exception ex) {
			return ResponseEntity.notFound()
					.build();
		}
	}

	// get user
	@GetMapping(value = "/{id}")
	private ResponseEntity<User> getUser(@PathVariable("id") Long id) {
		System.out.println("Id: " + id);
		try {
			User user = userService.getUserById(id);
			return ResponseEntity.ok(user); // restituisce l'utente
		} catch (Exception e) {
			return ResponseEntity.notFound()
					.build();
		}
	}

	// update user
	@PutMapping(value = "/{id}")
	private ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id) {
		try {
			User userNew = userService.updateUser(user, id);
			return ResponseEntity.ok(userNew);
		} catch (NotFoundUserException ex) {
			return ResponseEntity.notFound()
					.build();
		} catch (Exception ex) {
			return ResponseEntity.badRequest()
					.build();
		}
	}

	// delete user
	@DeleteMapping(value = "/{id}")
	private ResponseEntity<Boolean> deleteUser(@RequestBody User user, @PathVariable Long id) {
		try {
			userService.deleteUser(id, user.getPassword());
			return ResponseEntity.ok(true);
		} catch (InvalidPasswordException e) {
			return ResponseEntity.badRequest()
					.build();
		} catch (Exception ex) {
			return ResponseEntity.notFound()
					.build();
		}
	}

	// per immettere il pagamento nella app, quando compri qualcosa si usa il
	// servizio agentia di credito
	// il pagamento viene effetuto da una banca e ti restituira un token
	// funziona in modalita rest
}
