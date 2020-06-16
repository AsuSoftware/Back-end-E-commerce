package com.asusoftware.ecommerce.controllers;

import java.util.List;

import com.asusoftware.ecommerce.dto.AdDto;
import com.asusoftware.ecommerce.exceptions.NotFoundUserException;
import com.asusoftware.ecommerce.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
			return ResponseEntity.ok(userService.insertAd(adDto, id));
		} catch (NotFoundUserException ex) {
			return ResponseEntity.notFound()
					.build();
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.build();
		}
	}

}
