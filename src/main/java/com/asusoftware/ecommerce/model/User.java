package com.asusoftware.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    // serve per mappare un campo di business in json
    @JsonProperty("id") // forza la produzione di questo campo
    private Long id;

    @Column(name = "first_name")
    @NotNull
    private String name;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    // @TODO change int to Date and search how to storing date on database
    @Column(name = "user_birthday")
    @NotNull
    private Date birthday;

    @Column(name = "gender")
    @NotNull
    private String gender;

    // @TODO search how to send confirmation email when users register on the app or order something in this app
    @Column(name = "email")
    @NotNull
    private String email;

    // @TODO search how to hidden the password in database, so need to be encoded laterally
    @Column(name = "password")
    @NotNull
    private String password;

    // responsabile per il collegamento ad un'altra tabella
    // mappedBy... Indica che non deve crearsi questa tabella perchè lo fa già la tabella Ad
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // un user a molti annunci, mappedBy si riferisce al field dove e stata fatta la foreinKey
    private List<Ad> ads;


}
