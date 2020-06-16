package com.asusoftware.ecommerce.controllers;

import com.asusoftware.ecommerce.dto.UserDto;
import com.asusoftware.ecommerce.exceptions.InvalidPassword;
import com.asusoftware.ecommerce.exceptions.NotFoundUser;
import com.asusoftware.ecommerce.model.User;
import com.asusoftware.ecommerce.services.UserServices;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*") // Abilitazione delle CORS - Cross Origin Resource Sharing
@RequestMapping("api")
public class UserController {

    private UserServices userServices;


    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    // add user
   // @PostMapping(value = "create",consumes = "application/json", produces = "application/json")
    @PostMapping(value = "create")
    private void createUser(@RequestBody User user) {
      userServices.insertUser(user);
    }

    // login
    @GetMapping(value = "login/{email}/{password}")
    private ResponseEntity<UserDto> getUser(@PathVariable("email") String email,@PathVariable("password") String password) {
        UserDto user = userServices.getLogin(email, password);
        if(user == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(user);
    }

    // get all users
    @GetMapping(value = "users")
    private ResponseEntity<List<UserDto>> getUsers() {
        System.out.println("first");
        try {
            return ResponseEntity.ok(userServices.getUsers());
        } catch (Exception ex) {
            System.out.println("second");
            return ResponseEntity.notFound().build();
        }
    }

    // get user
    @GetMapping(value = "profile/{id}")
    private ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        System.out.println("Id: " + id);
        try {
            User user = userServices.getUserById(id);
            return ResponseEntity.ok(user); // restituisce l'utente
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // update user
    @PutMapping(value = "update/{id}")
    private ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id) {
        try {
            User userNew = userServices.updateUser(user, id);
            return ResponseEntity.ok(userNew);
        } catch (NotFoundUser ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    // delete user
    @DeleteMapping(value = "delete/{id}")
    private ResponseEntity<Boolean> deleteUser(@RequestBody User user, @PathVariable Long id) {
        try {
            userServices.deleteUser(id, user.getPassword());
            return ResponseEntity.ok(true);
        } catch (InvalidPassword e) {
           return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // per immettere il pagamento nella app, quando compri qualcosa si usa il
    // servizio agentia di credito
    // il pagamento viene effetuto da una banca e ti restituira un token
    // funziona in modalita rest
}
