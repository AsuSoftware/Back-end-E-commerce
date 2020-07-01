package com.asusoftware.ecommerce.model;


import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "image_product", length = 9999999)
    @NotNull
    private String image;


    @ManyToOne // molti annunci su uno user
    @JoinColumn(name = "ad_id") // fa il join con l'altra colonna di un'altra Entità
    private Ad ad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

}
