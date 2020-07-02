package com.asusoftware.ecommerce.controllers;

import java.util.List;
import java.util.Optional;

import com.asusoftware.ecommerce.dto.AdDto;
import com.asusoftware.ecommerce.exceptions.NotFoundAdException;
import com.asusoftware.ecommerce.exceptions.NotFoundUserException;
import com.asusoftware.ecommerce.model.Ad;
import com.asusoftware.ecommerce.services.AdServiceImpl;
import com.asusoftware.ecommerce.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/ads")
public class AdsController {

	private final AdServiceImpl adService;


	// get all ads
	@GetMapping
	public ResponseEntity<List<AdDto>> getAllAds() {
		try {
			List<AdDto> ads = adService.findAllAds();
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
	public ResponseEntity<AdDto> getAd(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(adService.findAdById(id));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.build();
		}
	}

	@PostMapping("/{id}")
	public ResponseEntity<AdDto> addAd(@RequestBody AdDto adDto, @PathVariable("id") Long id) {
		try {
			Optional<Ad> ad = adService.insertAd(adDto, id);
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
	public ResponseEntity<AdDto> updateAd(@RequestBody AdDto adDto, @PathVariable("userId") Long userId, @PathVariable("adId") Long adId) {
		try {
			System.out.println(userId + " si : " + adId);
			AdDto ad = adService.updateAd(adDto, userId, adId);
			return ResponseEntity.ok(ad);
		} catch (NotFoundUserException ex) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@ResponseStatus(HttpStatus.OK) // e una maniera più semplice, per non usare responseEntity
	@DeleteMapping("/{id}")
	public void deleteAd(@PathVariable("id") Long id) {
		adService.deleteAd(id);
	}

}
