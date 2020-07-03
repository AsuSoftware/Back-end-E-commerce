package com.asusoftware.ecommerce.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ad")
@Getter // cosi genera Spring automaticamente i metodi get()
@Setter
@ToString // Genera automaticamente il metodo ToString()
public class Ad {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String titleProduct;

    @Column(name = "description_product")
    private String descriptionProduct;

    @Column(name = "price")
    private Double priceProduct;

    @Column(name = "category")
    private String category; // if is a electronics, or a book

    // responsabile per il collegamento alla tabella User con Ad tramite id
    @ManyToOne // molti annunci su uno user
    @JoinColumn(name = "user_id")
    private User user;

    // mappedBy indica che questa Entità non e responsabile per la relazione, bensi lo e l'altra entità
    // mappedBy si riferisce al nome della proprietà dell'associazione sul lato proprietario.
    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL) // un user a molti annunci, mappedBy si riferisce al field dove e stata fatta la foreinKey
    private List<Image> images;


}
