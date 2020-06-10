package com.asusoftware.ecommerce.services;

import com.asusoftware.ecommerce.dto.UserDto;
import com.asusoftware.ecommerce.exceptions.InvalidPassword;
import com.asusoftware.ecommerce.exceptions.NotFoundUser;
import com.asusoftware.ecommerce.model.User;
import com.asusoftware.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserServices {

    UserRepository repoUser;

    public UserServiceImpl(UserRepository userRepository) {
        repoUser = userRepository;
    }

    // get users
    @Override
    public List<UserDto> getUsers() {
        List<UserDto> users = repoUser.findAll().stream().map(user -> {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setLastName(user.getLastName());
            userDto.setBirthday(user.getBirthday());
            userDto.setEmail(user.getEmail());
            userDto.setGender(user.getGender());
            return userDto;
        }).collect(Collectors.toList());

        return users;
    }

    // add user
    @Override
    public Optional<User> insertUser(User user) {
        repoUser.save(user);
        return repoUser.findById(user.getId());
    }

    // get user by id
    @Override
    public User getUserById(Long id) throws Exception {
        System.out.println(repoUser.findById(id));
        return repoUser.findById(id).orElseThrow(Exception::new);
    }

    // method for login user
    @Override
    public User getLogin(String email, String password) {
        return repoUser.getLogin(email, password);
    }

    // update user
    @Override
    public User updateUser(User user, Long id) {
        User userNew = repoUser.findById(id).orElseThrow(NotFoundUser::new);
        userNew.setName(user.getName());
        userNew.setLastName(user.getLastName());
        userNew.setGender(user.getGender());
        userNew.setBirthday(user.getBirthday());
        userNew.setPassword(user.getPassword());
        return repoUser.save(userNew);
    }

    // delete user
    @Override
    public void deleteUser(Long id, String password) throws Exception {
     User user = repoUser.findById(id).orElseThrow(Exception::new);
     if(user.getPassword().equals(password)) {
         repoUser.deleteById(id);
     } else {
         throw new InvalidPassword();
     }
    }
}
