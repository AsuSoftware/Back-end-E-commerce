package com.asusoftware.ecommerce.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.asusoftware.ecommerce.dto.AdDto;
import com.asusoftware.ecommerce.dto.UserDto;
import com.asusoftware.ecommerce.exceptions.InvalidPasswordException;
import com.asusoftware.ecommerce.exceptions.NotFoundUserException;
import com.asusoftware.ecommerce.model.Ad;
import com.asusoftware.ecommerce.model.User;
import com.asusoftware.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository repoUser;

	public UserServiceImpl(UserRepository userRepository) {
		repoUser = userRepository;
	}

	// get users
	@Override
	public List<UserDto> getUsers() {
		return repoUser.findAll()
				.stream()
				.map(this::convertUserInDto)
				.collect(Collectors.toList());
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
		return repoUser.findById(id)
				.orElseThrow(Exception::new);
	}

	// method for login user
	@Override
	public UserDto getLogin(String email, String password) {
		UserDto userDto = new UserDto();
		User user = repoUser.getLogin(email, password);
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setLastName(user.getLastName());
		userDto.setGender(user.getGender());
		userDto.setEmail(user.getEmail());
		userDto.setBirthday(user.getBirthday());
		return userDto;
	}

	// update user
	@Override
	public User updateUser(User user, Long id) {
		User userNew = repoUser.findById(id)
				.orElseThrow(NotFoundUserException::new);
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
		User user = repoUser.findById(id)
				.orElseThrow(Exception::new);
		if (user.getPassword()
				.equals(password)) {
			repoUser.deleteById(id);
		} else {
			throw new InvalidPasswordException();
		}
	}

	// Ads
	// get all ads in database
	@Override
	public List<AdDto> getAllAds() {
		return repoUser.getAds()
				.stream()
				.map(this::convertAdInDto)
				.collect(Collectors.toList());
	}

	@Override
	public AdDto insertAd(AdDto adDto, Long id) {
		User user = repoUser.findById(id)
				.orElseThrow(NotFoundUserException::new);
		user.getAds()
				.add(convertAdInEntity(adDto));
		repoUser.save(user);
		Ad ad = user.getAds()
				.get(user.getAds()
						.size() - 1);

		return convertAdInDto(ad);
	}

	@Override
	public AdDto getAdById(Long id) {
		Ad ad = repoUser.getAdById(id);
		return convertAdInDto(ad);
	}

	@Override
	public AdDto updateAd(AdDto adDto, Long userId, Long adId) {
		User user = repoUser.findById(userId)
				.orElseThrow(NotFoundUserException::new);
		Ad ad = user.getAds()
				.get((int) (long) adId);
		user.getAds()
				.add((int) (long) ad.getId(), convertAdInEntity(adDto));
		return convertAdInDto(ad);
	}

	@Override
	public void deleteAd(Long id) {

	}

	// metodo per conversione da entità user a Dto
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

	// metodo per conversione da Dto user a Entità
	private User convertUserInDto(UserDto userDto) {
		User user = new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setLastName(userDto.getLastName());
		user.setBirthday(userDto.getBirthday());
		user.setGender(userDto.getGender());
		user.setEmail(userDto.getEmail());
		return user;
	}

	// metodo per conversione da entità ad a Dto
	private AdDto convertAdInDto(Ad ad) {
		AdDto adDto = new AdDto();
		adDto.setId(ad.getId());
		adDto.setPriceProduct(ad.getPriceProduct());
		adDto.setImageProduct(ad.getImageProduct());
		adDto.setDescriptionProduct(ad.getDescriptionProduct());
		adDto.setTitleProduct(ad.getTitleProduct());
		adDto.setCategory(ad.getCategory());
		return adDto;
	}

	// metodo per conversione da Dto ad a Entità
	private Ad convertAdInEntity(AdDto adDto) {
		Ad ad = new Ad();
		ad.setId(adDto.getId());
		ad.setPriceProduct(adDto.getPriceProduct());
		ad.setImageProduct(adDto.getImageProduct());
		ad.setDescriptionProduct(adDto.getDescriptionProduct());
		ad.setTitleProduct(adDto.getTitleProduct());
		ad.setCategory(adDto.getCategory());
		return ad;
	}
}
