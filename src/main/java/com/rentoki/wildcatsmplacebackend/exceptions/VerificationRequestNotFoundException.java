package com.rentoki.wildcatsmplacebackend.exceptions;

public class VerificationRequestNotFoundException extends RuntimeException {
    public VerificationRequestNotFoundException(String message) {
        super(message);
    }
}
