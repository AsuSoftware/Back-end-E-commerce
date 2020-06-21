package com.asusoftware.ecommerce.dto;

import com.asusoftware.ecommerce.model.Image;

import java.util.List;

public class AdDto {

    private Long id;
    private List<String> images;
    private String titleProduct;
    private String descriptionProduct;
    private Double priceProduct;
    private String category;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getTitleProduct() {
        return titleProduct;
    }

    public void setTitleProduct(String titleProduct) {
        this.titleProduct = titleProduct;
    }

    public String getDescriptionProduct() {
        return descriptionProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }

    public Double getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(Double priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "AdDto{" +
                "id=" + id +
                ", image=" + images +
                ", titleProduct='" + titleProduct + '\'' +
                ", descriptionProduct='" + descriptionProduct + '\'' +
                ", priceProduct=" + priceProduct +
                ", category='" + category + '\'' +
                '}';
    }
}
