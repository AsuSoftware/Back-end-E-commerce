package com.asusoftware.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
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
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL) // un user a molti annunci, mappedBy si riferisce al field dove e stata fatta la foreinKey
    private List<Ad> ads;

    // Getters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Ad> getAds() {
        return ads;
    }

    // Setters


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
