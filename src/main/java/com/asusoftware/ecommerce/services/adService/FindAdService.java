package com.asusoftware.ecommerce.services.adService;

import com.asusoftware.ecommerce.dto.AdDto;

import java.util.List;

public interface FindAdService {
    List<AdDto> findAllAds();
    AdDto findAdById(Long id);
}
