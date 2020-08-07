package com.asusoftware.ecommerce.services.adService;

import com.asusoftware.ecommerce.dto.AdDto;

public interface UpdateAdService {
    void updateAd(AdDto adDto, Long userId, Long adId);
}
