package com.asusoftware.ecommerce.exceptions;

public class NotFoundUser extends RuntimeException {
    public NotFoundUser() {
        super("User not found");
    }
}
