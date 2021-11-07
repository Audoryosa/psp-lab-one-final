package com.audriuskumpis.labOneThird.exception;

public class BadPasswordException extends RuntimeException {

    public BadPasswordException(String message) {
        super(message);
    }
}
