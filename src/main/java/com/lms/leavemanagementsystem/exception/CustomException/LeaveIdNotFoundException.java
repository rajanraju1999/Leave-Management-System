package com.lms.leavemanagementsystem.exception.CustomException;

import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Super;

public class LeaveIdNotFoundException extends RuntimeException{
    public LeaveIdNotFoundException(String s) {
        super(s);
    }
}
