package com.lms.leavemanagementsystem.exception.CustomException;

public class JwtException extends RuntimeException{
    public JwtException(String message) {
        super(message);
    }
}
