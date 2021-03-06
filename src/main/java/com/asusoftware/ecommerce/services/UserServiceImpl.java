package com.asusoftware.ecommerce.services;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import com.asusoftware.ecommerce.dto.UserDto;
import com.asusoftware.ecommerce.exceptions.InvalidPasswordException;
import com.asusoftware.ecommerce.exceptions.NotFoundUserException;
import com.asusoftware.ecommerce.model.User;
import com.asusoftware.ecommerce.repository.UserRepository;
import com.asusoftware.ecommerce.services.userService.CreateUserService;
import com.asusoftware.ecommerce.services.userService.DeleteUserService;
import com.asusoftware.ecommerce.services.userService.FindUserService;
import com.asusoftware.ecommerce.services.userService.UpdateUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // (Lombok) ci crea lui il costruttore con gli argomenti(dipenedenze)
public class UserServiceImpl implements CreateUserService, UpdateUserService, FindUserService, DeleteUserService {

	private final UserRepository repoUser;


	@Override
	public List<UserDto> findUsers() {
		return repoUser.findAll()
				.stream()
				.map(this::convertUserInDto)
				.collect(Collectors.toList());
	}


	@Override
	public void insertUser(User user) {
		repoUser.save(user);
	}


	@Override
	public UserDto findUserById(Long id) {
		User user = repoUser.findById(id)
				.orElseThrow(NotFoundUserException::new);
		return convertUserInDto(user);
	}


	@Override
	public UserDto findByEmailAndPassword(String email, String password) {
			User user = repoUser.findByEmailAndPassword(email, password).orElseThrow(NotFoundUserException::new);
			return convertUserInDto(user);
	}


	@Override
	public void updateUser(UserDto userDto, Long id) {
		User userNew = repoUser.findById(id)
				.orElseThrow(NotFoundUserException::new);
		User user = convertUserDtoInEntity(userDto);
		userNew.setName(user.getName());
		userNew.setLastName(user.getLastName());
		userNew.setGender(user.getGender());
		userNew.setBirthday(user.getBirthday());
		userNew.setPassword(user.getPassword());
		repoUser.save(userNew);
	}


	@Override
	public void deleteUser(Long id, String password) {
		User user = repoUser.findById(id).orElseThrow(NotFoundUserException::new);
		if (user.getPassword().equals(password)) {
			repoUser.deleteById(id);
		} else {
			throw new InvalidPasswordException();
		}
	}


	private UserDto convertUserInDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setLastName(user.getLastName());
		userDto.setBirthday(user.getBirthday());
		userDto.setGender(user.getGender());
		userDto.setEmail(user.getEmail());
		return userDto;
	}

	private User convertUserDtoInEntity(UserDto userDto) {
		User user = new User();
		user.setName(userDto.getName());
		user.setLastName(userDto.getLastName());
		user.setGender(userDto.getGender());
		user.setBirthday(userDto.getBirthday());
		user.setPassword(userDto.getPassword());
		return user;
	}


}
