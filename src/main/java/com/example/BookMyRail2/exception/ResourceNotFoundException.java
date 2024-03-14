package com.example.BookMyRail2.exception;

public class ResourceNotFoundException extends RuntimeException{



    public ResourceNotFoundException(String resourceName) {
        super(resourceName);
    }
}
