package com.asusoftware.ecommerce.repository;

import com.asusoftware.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT p FROM User p WHERE p.email = :email AND p.password = :password")
    User getLogin(@Param(value = "email") String email, @Param(value = "password") String password);
}
