package com.asusoftware.ecommerce.controllers;

import java.util.List;

import com.asusoftware.ecommerce.dto.LoginDto;
import com.asusoftware.ecommerce.dto.UserDto;
import com.asusoftware.ecommerce.exceptions.InvalidPasswordException;
import com.asusoftware.ecommerce.exceptions.NotFoundUserException;
import com.asusoftware.ecommerce.model.User;
import com.asusoftware.ecommerce.services.AdServiceImpl;
import com.asusoftware.ecommerce.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*") // Abilitazione delle CORS - Cross Origin Resource Sharing
@RequestMapping("/api/users")
public class UserController {

	private final UserServiceImpl userService;


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
	//@ResponseStatus(HttpStatus.ACCEPTED)
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
			return ResponseEntity.ok(userService.findUsers());
		} catch (Exception ex) {
			return ResponseEntity.notFound()
					.build();
		}
	}

	// get user
	@GetMapping(value = "/{id}")
	private ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
		System.out.println("Id: " + id);
		try {
			UserDto user = userService.findUserById(id);
			return ResponseEntity.ok(user); // restituisce l'utente
		} catch (Exception e) {
			return ResponseEntity.notFound()
					.build();
		}
	}

	// update user
	@PutMapping(value = "/{id}")
	private ResponseEntity<User> updateUser(@RequestBody UserDto userDto, @PathVariable Long id) {
		try {
			User userNew = userService.updateUser(userDto, id);
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
	private ResponseEntity<Boolean> deleteUser(@RequestBody UserDto userDto, @PathVariable Long id) {
		try {
			userService.deleteUser(id, userDto.getPassword());
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
