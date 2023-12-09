package com.lms.leavemanagementsystem.exception;

import com.lms.leavemanagementsystem.dto.HalfDay;
import com.lms.leavemanagementsystem.exception.CustomException.EnumValidationException;
import com.lms.leavemanagementsystem.exception.CustomException.HalfDayLeaveException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(HalfDayLeaveException.class)
    public ResponseEntity<String> EmptyRollNumberException(HalfDayLeaveException halfDayLeaveException) {
        return new ResponseEntity<>("When you are applying half day leaves the start date and end date should be same ", HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


    @ExceptionHandler(EnumValidationException.class)
    public ResponseEntity< String> EnumValidationException(EnumValidationException ex) {



        return new ResponseEntity<>( ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}