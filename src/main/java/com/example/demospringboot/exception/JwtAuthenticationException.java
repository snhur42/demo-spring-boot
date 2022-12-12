package com.example.demospringboot.exception;

public class JwtAuthenticationException extends RuntimeException {
    public JwtAuthenticationException(String message) {
        super(message);
    }
}
