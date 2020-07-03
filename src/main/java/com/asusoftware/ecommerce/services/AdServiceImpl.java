package com.asusoftware.ecommerce.services;

import com.asusoftware.ecommerce.dto.AdDto;
import com.asusoftware.ecommerce.dto.ImageDto;
import com.asusoftware.ecommerce.exceptions.NotFoundAdException;
import com.asusoftware.ecommerce.exceptions.NotFoundUserException;
import com.asusoftware.ecommerce.model.Ad;
import com.asusoftware.ecommerce.model.Image;
import com.asusoftware.ecommerce.model.User;
import com.asusoftware.ecommerce.repository.AdRepository;
import com.asusoftware.ecommerce.repository.ImageRepository;
import com.asusoftware.ecommerce.repository.UserRepository;
import com.asusoftware.ecommerce.services.adService.CreateAdService;
import com.asusoftware.ecommerce.services.adService.DeleteAdService;
import com.asusoftware.ecommerce.services.adService.FindAdService;
import com.asusoftware.ecommerce.services.adService.UpdateAdService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements CreateAdService, UpdateAdService, FindAdService, DeleteAdService {

    private final UserRepository repoUser;
    private final AdRepository adRepository;
    private final ImageRepository imageRepository;

    @Override
    public void insertAd(AdDto adDto, Long id) {
        User user = repoUser.findById(id).orElseThrow(NotFoundUserException::new); // find the current user
        Ad ad = convertAdDtoInEntity(adDto);
        ad.setUser(user);
        adRepository.save(ad);
        List<Image> images = adDto.getImages().stream().map(imageDto -> {
            Image img = new Image();
            img.setImage(imageDto.getImage());
            img.setAd(ad); // pe Entitatea imagine setez ad-ul
            return img;
        }).collect(Collectors.toList());
        imageRepository.saveAll(images);
    }

    @Override
    public void deleteAd(Long id) {
        try {
            adRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundAdException();
        }

    }

    @Override
    public List<AdDto> findAllAds() {
        return adRepository.findAll().stream().map(this::convertAdInDto).collect(Collectors.toList());
    }

    @Override
    public AdDto findAdById(Long id) {
        Ad ad = adRepository.findById(id).orElseThrow(NotFoundAdException::new);
        return convertAdInDto(ad);
    }

    @Override
    public void updateAd(AdDto adDto, Long userId, Long adId) {
        repoUser.findById(userId).orElseThrow(NotFoundUserException::new);
        Ad ad = adRepository.findById(adId).orElseThrow(NotFoundAdException::new);
        List<Image> images = adDto.getImages().stream().map(this::convertImageDtoInImage).collect(Collectors.toList());
        ad.setTitleProduct(adDto.getTitleProduct());
        ad.setDescriptionProduct(adDto.getDescriptionProduct());
        ad.setCategory(adDto.getCategory());
        ad.setPriceProduct(adDto.getPriceProduct());
        ad.setImages(images);
        adRepository.save(ad);
    }


    private AdDto convertAdInDto(Ad ad) {
        AdDto adDto = new AdDto();
        adDto.setId(ad.getId());
        adDto.setPriceProduct(ad.getPriceProduct());
        adDto.setImages(ad.getImages().stream().map(this::convertImageInDto).collect(Collectors.toList()));
        adDto.setDescriptionProduct(ad.getDescriptionProduct());
        adDto.setTitleProduct(ad.getTitleProduct());
        adDto.setCategory(ad.getCategory());
        return adDto;
    }


    private Ad convertAdDtoInEntity(AdDto adDto) {
        Ad ad = new Ad();
        ad.setTitleProduct(adDto.getTitleProduct());
        ad.setCategory(adDto.getCategory());
        ad.setPriceProduct(adDto.getPriceProduct());
        ad.setDescriptionProduct(adDto.getDescriptionProduct());
        return ad;
    }

    private ImageDto convertImageInDto(Image image) {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(image.getId());
        imageDto.setImage(image.getImage());
        return imageDto;
    }

    private Image convertImageDtoInImage(ImageDto imageDto) {
        Image image = new Image();
        image.setId(imageDto.getId());
        image.setImage(imageDto.getImage());
        return image;
    }

}
