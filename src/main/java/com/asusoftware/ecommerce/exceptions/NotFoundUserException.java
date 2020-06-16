package com.asusoftware.ecommerce.exceptions;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException() {
        super("User not found");
    }
}
