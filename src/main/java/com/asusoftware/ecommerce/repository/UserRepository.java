package com.asusoftware.ecommerce.repository;

import com.asusoftware.ecommerce.model.Ad;
import com.asusoftware.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT p FROM User p WHERE p.email = :email AND p.password = :password")
    Optional<User> findByEmailAndPassword(@Param(value = "email") String email, @Param(value = "password") String password);

    // return user with current ad id
    @Query("SELECT p FROM Ad p WHERE p.id = :id")
    Optional<Ad> findAdById(@Param(value = "id") Long id);

    // return all ads
    @Query("SELECT p.ads FROM User p")
    Optional<Ad> getAllAds();

    // return ads by specific category
}
