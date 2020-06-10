package com.asusoftware.ecommerce.services;

import com.asusoftware.ecommerce.dto.UserDto;
import com.asusoftware.ecommerce.model.User;


import java.util.List;
import java.util.Optional;

public interface UserServices {

    List<UserDto> getUsers();
    Optional<User> insertUser(User user);
    User getUserById(Long id) throws Exception;
    User getLogin(String email, String password);
    User updateUser(User user, Long id);
    void deleteUser(Long id, String password) throws Exception;
}
