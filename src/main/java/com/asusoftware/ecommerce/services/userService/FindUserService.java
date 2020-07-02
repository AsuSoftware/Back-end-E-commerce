package com.asusoftware.ecommerce.services.userService;

import com.asusoftware.ecommerce.dto.UserDto;

import java.util.List;

public interface FindUserService {

    List<UserDto> findUsers();
    UserDto findUserById(Long id) throws Exception;
    UserDto findByEmailAndPassword(String email, String password);
}
