package com.lms.leavemanagementsystem.util;

import com.lms.leavemanagementsystem.dto.EmployeeDto;
import com.lms.leavemanagementsystem.dto.LeaveDto;
import com.lms.leavemanagementsystem.entity.Employee;
import com.lms.leavemanagementsystem.entity.Leave;
import com.lms.leavemanagementsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.Optional;

@Component
public class Convert {

    @Autowired
    EmployeeRepository employeeRepository;

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



    public Leave convertToLeave(LeaveDto leaveDto)
    {
        Optional<Employee> employeeOptional = employeeRepository.findById(leaveDto.getEmployeeID());
        LocalDate currentDate = LocalDate.now();

        LocalDate startDate = LocalDate.parse(leaveDto.getStartDate());
        LocalDate endDate = LocalDate.parse(leaveDto.getEndDate());
        long leavesAppliedDays = Period.between(startDate,endDate).getDays()+1;

        return Leave.builder()
                .employee(employeeOptional.orElse(null))
                .leaveType(leaveDto.getLeaveType())
                .startDate(LocalDate.parse(leaveDto.getStartDate()))
                .endDate(LocalDate.parse(leaveDto.getEndDate()))
                .reason(leaveDto.getReason())
                .adjustments(leaveDto.getAdjustments())
                .datePosted(currentDate)
                .leavesApplied(leavesAppliedDays)
                .leaveStatus("InProcess")
                .build();
    }
    public LeaveDto convertToLeaveDto(Leave leave){

        return LeaveDto.builder()
                .employeeID(leave.getEmployee().getEmployeeId())
                .leaveType(leave.getLeaveType())
                .startDate(leave.getStartDate().toString())
                .endDate(leave.getEndDate().toString())
                .reason(leave.getReason())
                .adjustments(leave.getAdjustments())
                .build();
    }
}
