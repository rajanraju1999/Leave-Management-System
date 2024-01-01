package com.lms.leavemanagementsystem.exception.CustomException;

public class UsernamePasswordException extends RuntimeException{
    public UsernamePasswordException(String message) {
        super(message);
    }
}
