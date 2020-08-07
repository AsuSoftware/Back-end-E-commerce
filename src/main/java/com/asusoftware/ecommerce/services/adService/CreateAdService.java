package com.asusoftware.ecommerce.services.adService;

import com.asusoftware.ecommerce.dto.AdDto;


public interface CreateAdService {
    void insertAd(AdDto adDto, Long id);
}
