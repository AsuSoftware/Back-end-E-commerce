package com.asusoftware.ecommerce.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AdDto {

    private Long id;
    private List<ImageDto> images;
    private String titleProduct;
    private String descriptionProduct;
    private Double priceProduct;
    private String category;


}
