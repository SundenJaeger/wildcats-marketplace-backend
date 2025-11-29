package com.rentoki.wildcatsmplacebackend.exceptions;

public class ResourceAlreadyBookmarkedException extends RuntimeException {
    public ResourceAlreadyBookmarkedException() {
        super();
    }

    public ResourceAlreadyBookmarkedException(String message) {
        super(message);
    }

    public ResourceAlreadyBookmarkedException(Throwable cause) {
        super(cause);
    }

    public ResourceAlreadyBookmarkedException(String message, Throwable cause) {
        super(message, cause);
    }
}
