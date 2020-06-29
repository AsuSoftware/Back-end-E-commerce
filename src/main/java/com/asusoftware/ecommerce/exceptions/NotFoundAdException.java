package com.asusoftware.ecommerce.exceptions;

public class NotFoundAdException extends RuntimeException {
    public NotFoundAdException() {
       super("Ad not found");
   }
}
