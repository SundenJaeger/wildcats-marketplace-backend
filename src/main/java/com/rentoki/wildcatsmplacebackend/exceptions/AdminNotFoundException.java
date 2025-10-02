package com.rentoki.wildcatsmplacebackend.exceptions;

public class AdminNotFoundException extends RuntimeException {
    public AdminNotFoundException(String message) {
        super(message);
    }
}
