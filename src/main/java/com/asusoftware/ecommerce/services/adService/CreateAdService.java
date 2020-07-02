package com.asusoftware.ecommerce.services.adService;

import com.asusoftware.ecommerce.dto.AdDto;
import com.asusoftware.ecommerce.model.Ad;

import java.util.Optional;

public interface CreateAdService {
    Optional<Ad> insertAd(AdDto adDto, Long id);
}
