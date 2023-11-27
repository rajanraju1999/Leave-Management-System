package com.lms.leavemanagementsystem.util;

import com.lms.leavemanagementsystem.dto.EmployeeDto;
import com.lms.leavemanagementsystem.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class Convert {


    public Employee convertToEmployee(EmployeeDto employeeDto){

       return Employee.builder()
                .employeeId(employeeDto.getEmployeeId())
                .employeeName(employeeDto.getEmployeeName())
                .email(employeeDto.getEmail())
                .department(employeeDto.getDepartment())
                .build();


    }


    public EmployeeDto convertToEmployeeDto(Employee employee){

        return  EmployeeDto.builder()
                .employeeId(employee.getEmployeeId())
                .employeeName(employee.getEmployeeName())
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .build();


    }
}
