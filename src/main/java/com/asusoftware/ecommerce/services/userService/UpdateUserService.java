package com.asusoftware.ecommerce.services.userService;

import com.asusoftware.ecommerce.dto.UserDto;
import com.asusoftware.ecommerce.model.User;

public interface UpdateUserService {
    void updateUser(UserDto userDto, Long id);
}
