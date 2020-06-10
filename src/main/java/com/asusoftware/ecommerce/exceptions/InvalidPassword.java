package com.asusoftware.ecommerce.exceptions;

public class InvalidPassword extends RuntimeException {
    public InvalidPassword() {
        super("Invalid password");
    }
}
