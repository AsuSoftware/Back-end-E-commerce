package com.asusoftware.ecommerce.model;

import javax.persistence.*;

@Entity
@Table(name = "ads")
public class Ad {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // @Todo search a way to insert image converted in base64 on the database
    @Column(name = "image")
    private String imageProduct;

    @Column(name = "title")
    private String titleProduct;

    @Column(name = "description")
    private String descriptionProduct;

    @Column(name = "price")
    private Double priceProduct;

    @Column(name = "category")
    private String category; // if is a electronics, or a book

    // responsabile per il collegamento alla tabella User con Ad
    @ManyToOne // molti annunci su uno user
    @JoinColumn(name = "user_id")
    private User user;


    // getters and setters

    public Long getId() {
        return id;
    }

    public String getTitleProduct() {
        return titleProduct;
    }

    public void setTitleProduct(String titleProduct) {
        this.titleProduct = titleProduct;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
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
}
