package com.asusoftware.ecommerce.controllers;

import com.asusoftware.ecommerce.dto.AdDto;
import com.asusoftware.ecommerce.exceptions.NotFoundUser;

import com.asusoftware.ecommerce.services.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/ads")
public class AdsController {

    private UserServices userServices;

    public AdsController(UserServices userServices) {
        this.userServices = userServices;
    }

    // get all ads
    @GetMapping
    private ResponseEntity<List<AdDto>> getAllAds() {
        try {
            List<AdDto> ads = userServices.getAllAds();
            return ResponseEntity.ok(ads);
        } catch (NotFoundUser ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // get specific ad with the id
    @GetMapping("{id}")
    private ResponseEntity<AdDto> getAd(@PathVariable Long id){
        try {
            return ResponseEntity.ok(userServices.getAdById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("add/{id}")
    private ResponseEntity<AdDto> addAd(@RequestBody AdDto adDto, @PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(userServices.insertAd(adDto, id));
        } catch (NotFoundUser ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
