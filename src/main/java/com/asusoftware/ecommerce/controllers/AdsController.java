package com.asusoftware.ecommerce.controllers;

import java.util.List;
import java.util.Optional;

import com.asusoftware.ecommerce.dto.AdDto;
import com.asusoftware.ecommerce.exceptions.NotFoundAdException;
import com.asusoftware.ecommerce.exceptions.NotFoundUserException;
import com.asusoftware.ecommerce.model.Ad;
import com.asusoftware.ecommerce.model.Image;
import com.asusoftware.ecommerce.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/ads")
public class AdsController {

	private final UserService userService;

	public AdsController(UserService userService) {
		this.userService = userService;
	}

	// get all ads
	@GetMapping
	private ResponseEntity<List<AdDto>> getAllAds() {
		try {
			List<AdDto> ads = userService.getAllAds();
			return ResponseEntity.ok(ads);
		} catch (NotFoundUserException ex) {
			return ResponseEntity.notFound()
					.build();
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.build();
		}
	}

	// get specific ad with the id
	@GetMapping("/{id}")
	private ResponseEntity<AdDto> getAd(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(userService.getAdById(id));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.build();
		}
	}

	@PostMapping("/{id}")
	private ResponseEntity<AdDto> addAd(@RequestBody AdDto adDto, @PathVariable("id") Long id) {
		try {
			Optional<Ad> ad = userService.insertAd(adDto, id);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (NotFoundUserException ex) {
			return ResponseEntity.notFound()
					.build();
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.build();
		}
	}

	@PutMapping("/{userId}/{adId}")
	private ResponseEntity<AdDto> updateAd(@RequestBody AdDto adDto, @PathVariable("userId") Long userId, @PathVariable("adId") Long adId) {
		try {
			System.out.println(userId + " si : " + adId);
			AdDto ad = userService.updateAd(adDto, userId, adId);
			return ResponseEntity.ok(ad);
		} catch (NotFoundUserException ex) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/{id}")
	private ResponseEntity deleteAd(@PathVariable("id") Long id) {
		try {
			userService.deleteAd(id);
			return ResponseEntity.ok().build();
		} catch (NotFoundAdException ex) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

}
