package com.lms.leavemanagementsystem.controller;

import com.lms.leavemanagementsystem.dto.EmployeeDto;
import com.lms.leavemanagementsystem.dto.LeaveDto;
import com.lms.leavemanagementsystem.dto.LeaveDtoApprove;
import com.lms.leavemanagementsystem.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/get/leave/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<LeaveDto>> getLeavesByID(@PathVariable Long id)
    {


        return new ResponseEntity<>(employeeService.getLeavesById(id), HttpStatus.OK);

    }

    @PutMapping("/approve/leave")
    public ResponseEntity<?>  approveLeave(@RequestBody @Valid LeaveDtoApprove leaveDtoApprove)
    {
        employeeService.approveLeave(leaveDtoApprove);

        return new ResponseEntity<>("", HttpStatus.OK);

    }



}
