package org.example.userauthservice_begmay2025.exceptions;

public class UserAlreadySignedInException extends RuntimeException {

    public UserAlreadySignedInException(String message) {
        super(message);
    }
}
