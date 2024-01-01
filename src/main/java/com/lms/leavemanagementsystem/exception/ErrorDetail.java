package com.lms.leavemanagementsystem.exception;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorDetail {

    private HttpStatus status;
    private String message;
    private String detail;

}