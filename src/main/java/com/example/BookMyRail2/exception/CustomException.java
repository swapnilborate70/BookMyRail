package com.example.BookMyRail2.exception;

public class CustomException extends RuntimeException{

    public CustomException(String resourceName) {
        super(resourceName);
    }

}