package com.lms.leavemanagementsystem.controller;

import com.lms.leavemanagementsystem.dto.EmployeeDto;
import com.lms.leavemanagementsystem.dto.LeaveDto;
import com.lms.leavemanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/employee")
public class EmployeeController {


    @Autowired
    EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDto employeeDto){


      employeeService.createEmployee(employeeDto);

      return new ResponseEntity<>("", HttpStatus.OK);

    }
    @PostMapping("/apply/leave")
    public ResponseEntity<?> applyLeave(@RequestBody LeaveDto leaveDto)
    {
        employeeService.applyLeave(leaveDto);
        return new ResponseEntity<>("", HttpStatus.OK);

    }

    @GetMapping("/get/leave/{id}")
    public ResponseEntity<List<LeaveDto>> getLeavesByID(@PathVariable Long id)
    {


        return new ResponseEntity<>(employeeService.getLeavesById(id), HttpStatus.OK);

    }



}
