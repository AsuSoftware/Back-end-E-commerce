package com.asusoftware.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.asusoftware.ecommerce.model")
@EnableJpaRepositories(basePackages = "com.asusoftware.ecommerce.repository")
@ComponentScan(basePackages = {"com.asusoftware.ecommerce.services", "com.asusoftware.ecommerce.controllers"})
public class BackEndECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEndECommerceApplication.class, args);
	}

}
