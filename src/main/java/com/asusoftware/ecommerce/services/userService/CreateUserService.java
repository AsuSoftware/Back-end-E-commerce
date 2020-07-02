package com.asusoftware.ecommerce.services.userService;

import com.asusoftware.ecommerce.model.User;

import java.util.Optional;
/*
 * Abbiamo diviso il compito in più interfacce cosi ognina si preocupa di svolgere il proprio ruolo
 * Single Responsibility Principle
 */
public interface CreateUserService {

    Optional<User> insertUser(User user);
}
