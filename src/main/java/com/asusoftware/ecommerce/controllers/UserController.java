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
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*") // Abilitazione delle CORS - Cross Origin Resource Sharing
@RequestMapping("/api/users")
public class UserController {

	private final UserServiceImpl userService;


	// @PostMapping(value = "create",consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	private void createUser(@RequestBody User user) {
		 userService.insertUser(user);
	}


	@ResponseStatus(HttpStatus.ACCEPTED)
	@PostMapping("login")
	private UserDto findByEmailAndPassword(@RequestBody LoginDto login) {
			return userService.findByEmailAndPassword(login.getEmail(), login.getPassword());
	}


	@ResponseStatus(HttpStatus.FOUND)
	@GetMapping
	private List<UserDto> getUsers() {
			return userService.findUsers();
	}


	@ResponseStatus(HttpStatus.FOUND)
	@GetMapping(value = "/{id}")
	private UserDto getUser(@PathVariable("id") Long id) {
			return userService.findUserById(id);
	}

	// update user
	@ResponseStatus(HttpStatus.OK)
	@PutMapping(value = "/{id}")
	private void updateUser(@RequestBody UserDto userDto, @PathVariable Long id) {
			userService.updateUser(userDto, id);
	}

	// delete user
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping(value = "/{id}")
	private void deleteUser(@RequestBody UserDto userDto, @PathVariable Long id) {
		userService.deleteUser(id, userDto.getPassword());
	}

	// per immettere il pagamento nella app, quando compri qualcosa si usa il
	// servizio agentia di credito
	// il pagamento viene effetuto da una banca e ti restituira un token
	// funziona in modalita rest
}
