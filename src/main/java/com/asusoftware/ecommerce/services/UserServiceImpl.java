package com.asusoftware.ecommerce.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.asusoftware.ecommerce.dto.AdDto;
import com.asusoftware.ecommerce.dto.UserDto;
import com.asusoftware.ecommerce.exceptions.InvalidPasswordException;
import com.asusoftware.ecommerce.exceptions.NotFoundUserException;
import com.asusoftware.ecommerce.model.Ad;
import com.asusoftware.ecommerce.model.Image;
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
	public UserDto findByEmailAndPassword(String email, String password) {
			User user = repoUser.findByEmailAndPassword(email, password).orElseThrow(NotFoundUserException::new);
			return convertUserInDto(user);
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
	// get all ads from database
	@Override
	public List<AdDto> getAllAds() {

		/*
		List<Ad> ads = repoUser.findAll().stream()
				.filter(user -> user.getAds() != null)
				.map(User::getAds)
				.flatMap(List::stream)
				.collect(Collectors.toList());
		List<AdDto> adsDto = convertAdsInDto(ads); */
	//	List<AdDto> ads = repoUser.getAllAds().stream().map(this::convertAdInDto).collect(Collectors.toList());
		List<User> users = repoUser.findAll().stream().filter(user -> user.getAds().size() > 0).collect(Collectors.toList());

		List<AdDto> adsDto = users.stream().map(User::getAds).map(this::convertAdsInDto).flatMap(List::stream).collect(Collectors.toList());
	/*	return repoUser.getAds()
				.stream()
				.map(this::convertAdInDto)
				.collect(Collectors.toList()); */
	    return adsDto;
	}

	@Override
	public AdDto insertAd(AdDto adDto, Long id) {

		// find the current user
		User user = repoUser.findById(id)
				.orElseThrow(NotFoundUserException::new);

		// setez userul pe ad
		Ad ad = new Ad();
		ad.setCategory(adDto.getCategory());
		ad.setDescriptionProduct(adDto.getDescriptionProduct());
		//ad.setImageProduct(adDto.getImageProduct());
		ad.setPriceProduct(adDto.getPriceProduct());
		ad.setTitleProduct(adDto.getTitleProduct());
		ad.setUser(user);

	/*	adDto.getImages()
				.stream().map(image -> {
					Image img = new Image();
			        img.setImage(image);
			        img.setAd(ad);
			        return img;
		}); */

	List<Image> images = new ArrayList<>();

		for (String image: adDto.getImages()) {
			Image img = new Image();
			// pe Entitatea image setez ad-ul
			img.setAd(ad);
			img.setImage(image);
			// adaug entitatea imagine in lista de pe ad
			images.add(img);
			System.out.println("proba");
		}

		//ad.getImages().add(image); // nullPointerException imi da
		ad.setImages(images);
		System.out.println("1");
		// setez ad-ul in lista de pe user
		user.getAds()
				.add(ad);
		System.out.println("2");
		System.out.println(user.getAds());
		repoUser.save(user);
		System.out.println("merge");
		return convertAdInDto(ad);
	}

	@Override
	public AdDto getAdById(Long id) {
		Optional<Ad> ad = repoUser.findAdById(id);
	/*	List<Ad> ad = user.getAds().stream()
				.filter(ad1 -> ad1.getId().equals(id))
				.collect(Collectors.toList()); */
		return convertAdInDtoOptional(ad);
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
		//adDto.getImages().add(ad.getImages());
		//adDto.setImageProduct(ad.getImageProduct());
		adDto.setDescriptionProduct(ad.getDescriptionProduct());
		adDto.setTitleProduct(ad.getTitleProduct());
		adDto.setCategory(ad.getCategory());
		return adDto;
	}

	// metodo per conversione da entità ad a Dto
	private AdDto convertAdInDtoOptional(Optional<Ad> ad) {
		AdDto adDto = new AdDto();
		adDto.setId(ad.get().getId());
		adDto.setPriceProduct(ad.get().getPriceProduct());
		//adDto.setImageProduct(ad.get().getImageProduct());
		adDto.setDescriptionProduct(ad.get().getDescriptionProduct());
		adDto.setTitleProduct(ad.get().getTitleProduct());
		adDto.setCategory(ad.get().getCategory());
		return adDto;
	}

	// metodo per conversione da entità ad a Dto con foreach
	private List<AdDto> convertAdsInDto(List<Ad> ads) {
		List<AdDto> adsDto = new ArrayList<>();
		for (Ad ad: ads) {
			AdDto adDto = new AdDto();
			adDto.setId(ad.getId());
			adDto.setPriceProduct(ad.getPriceProduct());
			//adDto.setImageProduct(ad.getImageProduct());
			adDto.setDescriptionProduct(ad.getDescriptionProduct());
			adDto.setTitleProduct(ad.getTitleProduct());
			adDto.setCategory(ad.getCategory());
			adsDto.add(adDto);
		}
		return adsDto;
	}

	// metodo per conversione da Dto ad a Entità
	private Ad convertAdInEntity(AdDto adDto) {
		Ad ad = new Ad();
		ad.setPriceProduct(adDto.getPriceProduct());
		//ad.setImageProduct(adDto.getImageProduct());
		ad.setDescriptionProduct(adDto.getDescriptionProduct());
		ad.setTitleProduct(adDto.getTitleProduct());
		ad.setCategory(adDto.getCategory());
		return ad;
	}

}
