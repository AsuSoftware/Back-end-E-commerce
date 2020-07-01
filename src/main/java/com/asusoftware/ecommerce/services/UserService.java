package com.asusoftware.ecommerce.services;

import com.asusoftware.ecommerce.dto.AdDto;
import com.asusoftware.ecommerce.dto.UserDto;
import com.asusoftware.ecommerce.model.Ad;
import com.asusoftware.ecommerce.model.User;


import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> getUsers();
    Optional<User> insertUser(User user);
    User getUserById(Long id) throws Exception;
    UserDto findByEmailAndPassword(String email, String password);
    User updateUser(User user, Long id);
    void deleteUser(Long id, String password) throws Exception;

    List<AdDto> getAllAds();
    Optional<Ad> insertAd(AdDto adDto, Long id);
    AdDto getAdById(Long id);
    AdDto updateAd(AdDto adDto, Long userId, Long adId);
    void deleteAd(Long id);

}
