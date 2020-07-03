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


	@ResponseStatus(HttpStatus.FOUND)
	@GetMapping
	public List<AdDto> getAllAds() {
		return adService.findAllAds();
	}


	@ResponseStatus(HttpStatus.FOUND)
	@GetMapping("/{id}")
	public AdDto getAd(@PathVariable Long id) {
		return adService.findAdById(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/{id}")
	public void addAd(@RequestBody AdDto adDto, @PathVariable("id") Long id) {
		adService.insertAd(adDto, id);
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{userId}/{adId}")
	public void updateAd(@RequestBody AdDto adDto, @PathVariable("userId") Long userId, @PathVariable("adId") Long adId) {
		adService.updateAd(adDto, userId, adId);
	}

	@ResponseStatus(HttpStatus.OK) // e una maniera più semplice, per non usare responseEntity
	@DeleteMapping("/{id}")
	public void deleteAd(@PathVariable("id") Long id) {
		adService.deleteAd(id);
	}

}
