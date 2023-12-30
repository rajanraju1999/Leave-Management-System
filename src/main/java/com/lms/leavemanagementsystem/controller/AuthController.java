package com.lms.leavemanagementsystem.controller;

import com.lms.leavemanagementsystem.dto.EmployeeDto;
import com.lms.leavemanagementsystem.dto.LoginDto;
import com.lms.leavemanagementsystem.entity.Employee;
import com.lms.leavemanagementsystem.repository.EmployeeRepository;
import com.lms.leavemanagementsystem.service.EmployeeService;
import com.lms.leavemanagementsystem.security.jwt.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtGenerator jwtGenerator;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody EmployeeDto employeeDto){

    if(employeeRepository.existsById(employeeDto.getEmployeeId())){
        return new ResponseEntity<>("employee already exists", HttpStatus.BAD_REQUEST);
    }

      Employee employee = Employee.builder()
                .employeeId(employeeDto.getEmployeeId())
                .employeeName(employeeDto.getEmployeeName())
                .email(employeeDto.getEmail())
                .password(passwordEncoder.encode(employeeDto.getPassword()))
                .department(employeeDto.getDepartment())
                .build();

        employeeRepository.save(employee);

        return new ResponseEntity<>("", HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){


        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUserName(),
                            loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtGenerator.generateToken(authentication);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace(); // or log the exception detai
            return new ResponseEntity<>("wrong cridentionals", HttpStatus.BAD_REQUEST);
        }
    }






}
