package com.lms.leavemanagementsystem.exception;

import ch.qos.logback.core.status.Status;
import com.lms.leavemanagementsystem.dto.HalfDay;
import com.lms.leavemanagementsystem.exception.CustomException.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(HalfDayLeaveException.class)
    public ResponseEntity<String> EmptyRollNumberException(HalfDayLeaveException halfDayLeaveException) {
        return new ResponseEntity<>("When you are applying half day leaves the start date and end date should be same ", HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(LeaveIdNotFoundException.class)
    public ResponseEntity<String> LeaveIdNotFoundException(LeaveIdNotFoundException leaveIdNotFoundException) {
        return new ResponseEntity<>(leaveIdNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(EmployeeIdNotFoundException.class)
    public ResponseEntity<String> EmployeeIdNotFoundException(EmployeeIdNotFoundException employeeIdNotFoundException) {
        return new ResponseEntity<>("Employee Id is wrong or not found in DB", HttpStatus.BAD_REQUEST);
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


        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                   HttpHeaders headers, HttpStatus status, WebRequest request) {
            String errorMessage = "Invalid request body format";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }





    @ExceptionHandler(LeaveApprovedException.class)
    public ResponseEntity< String> LeaveApprovedException(LeaveApprovedException ex) {

        return new ResponseEntity<>( ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ProblemDetail UsernamePasswordException(Exception ex) {

        ProblemDetail problem = null;
        if (ex instanceof UsernamePasswordException) {
            problem = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        }
        if (ex instanceof AccessDeniedException) {
            problem = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
        }

        return problem;
    }
}