package com.lms.leavemanagementsystem.controller;

import com.lms.leavemanagementsystem.dto.EmployeeDto;
import com.lms.leavemanagementsystem.dto.LeaveDto;
import com.lms.leavemanagementsystem.dto.LeaveDtoApprove;
import com.lms.leavemanagementsystem.exception.CustomException.UsernamePasswordException;
import com.lms.leavemanagementsystem.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/employee")
public class EmployeeController {


    @Autowired
    EmployeeService employeeService;


    @PostMapping("/apply/leave")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> applyLeave(@RequestBody @Valid LeaveDto leaveDto)
    {
        return new ResponseEntity<>(employeeService.applyLeave(leaveDto), HttpStatus.OK);

    }

    @GetMapping("/get/leave")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<LeaveDto>> getLeavesByID()
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails.getUsername());

        return new ResponseEntity<>(employeeService.getLeavesByEmail(userDetails.getUsername()), HttpStatus.OK);

    }


    @GetMapping("/get/leave/to/approve")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<LeaveDto>> getLeavesToApprove()
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails.getUsername());

        return new ResponseEntity<>(employeeService.getLeavesToApprove(userDetails.getUsername()), HttpStatus.OK);

    }


    @PutMapping("/approve/leave")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?>  approveLeave(@RequestBody @Valid LeaveDtoApprove leaveDtoApprove)
    {
        System.out.println("hi");
            employeeService.approveLeave(leaveDtoApprove);


        return new ResponseEntity<>("done", HttpStatus.OK);

    }



}
