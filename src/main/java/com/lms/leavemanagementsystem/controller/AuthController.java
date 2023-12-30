package com.lms.leavemanagementsystem.controller;

import com.lms.leavemanagementsystem.dto.EmployeeDto;
import com.lms.leavemanagementsystem.dto.LoginDto;
import com.lms.leavemanagementsystem.dto.RoleDto;
import com.lms.leavemanagementsystem.entity.Employee;
import com.lms.leavemanagementsystem.entity.Roles;
import com.lms.leavemanagementsystem.repository.EmployeeRepository;
import com.lms.leavemanagementsystem.repository.RoleRepository;
import com.lms.leavemanagementsystem.service.EmployeeService;
import com.lms.leavemanagementsystem.security.jwt.JwtGenerator;
import jakarta.transaction.Transactional;
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

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    RoleRepository roleRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody EmployeeDto employeeDto){

    if(employeeRepository.existsById(employeeDto.getEmployeeId())){
        return new ResponseEntity<>("employee already exists", HttpStatus.BAD_REQUEST);
    }
        List<Roles> roles = saveRoles(employeeDto.getRoles());

      Employee employee = Employee.builder()
                .employeeId(employeeDto.getEmployeeId())
                .employeeName(employeeDto.getEmployeeName())
                .email(employeeDto.getEmail())
                .password(passwordEncoder.encode(employeeDto.getPassword()))
                .department(employeeDto.getDepartment())
                .roles(roles)
                .build();

        employeeRepository.save(employee);

        return new ResponseEntity<>("", HttpStatus.OK);

    }

    private List<Roles> saveRoles(List<RoleDto> roleDtos) {
        List<Roles> roles = new ArrayList<>();
        for (RoleDto roleDto : roleDtos) {
            Roles role = roleRepository.findByRoleName(roleDto.getRoleName())
                    .orElseGet(() -> new Roles(roleDto.getRoleName()));
            roles.add(roleRepository.save(role));
        }
        return roles;
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
