package com.lms.leavemanagementsystem.util;

import com.lms.leavemanagementsystem.dto.EmployeeDto;
import com.lms.leavemanagementsystem.dto.LeaveDto;
import com.lms.leavemanagementsystem.entity.Employee;
import com.lms.leavemanagementsystem.entity.Leave;
import com.lms.leavemanagementsystem.exception.CustomException.HalfDayLeaveException;
import com.lms.leavemanagementsystem.repository.EmployeeRepository;
import com.lms.leavemanagementsystem.util.leavehandler.LeaveType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import static com.lms.leavemanagementsystem.dto.HalfDay.YES;
import static com.lms.leavemanagementsystem.dto.HalfDay.NO;

@Component
public class Convert {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ModelMapper modelMapper;


    public Employee convertToEmployee(EmployeeDto employeeDto){

       /* return Employee.builder()
                .employeeId(employeeDto.getEmployeeId())
                .employeeName(employeeDto.getEmployeeName())
                .email(employeeDto.getEmail())
                .department(employeeDto.getDepartment())
                .build();*/
       return  modelMapper.map(employeeDto,Employee.class);

    }

    public EmployeeDto convertToEmployeeDto(Employee employee){

        /*return  EmployeeDto.builder()
                .employeeId(employee.getEmployeeId())
                .employeeName(employee.getEmployeeName())
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .build();*/
        return  modelMapper.map(employee,EmployeeDto.class);
    }

    public Leave convertToLeave(LeaveDto leaveDto)
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<Employee> employeeOptional = Optional.ofNullable(employeeRepository.findByEmail(userDetails.getUsername()));

        LocalDate currentDate = LocalDate.now();

        LocalDate startDate = LocalDate.parse(leaveDto.getStartDate());
        LocalDate endDate = LocalDate.parse(leaveDto.getEndDate());
        Double leavesAppliedDays = (double) (Period.between(startDate, endDate).getDays() + 1);

           if ("YES".equals(leaveDto.getHalfDay())  && leavesAppliedDays != 1.0) {
               throw new HalfDayLeaveException();
           }else if("YES".equals(leaveDto.getHalfDay())){
               leavesAppliedDays = 0.5;
           }
        System.out.println(leavesAppliedDays);
        return Leave.builder()
                .employee(employeeOptional.orElse(null))
                .approverEmail(leaveDto.getApproverEmail())
                .leaveType(LeaveType.valueOf(leaveDto.getLeaveType()))
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

      /* return LeaveDto.builder()
                .employeeID(leave.getEmployee().getEmployeeId())
                .leaveType(leave.getLeaveType().toString())
                .startDate(leave.getStartDate().toString())
                .endDate(leave.getEndDate().toString())
                .reason(leave.getReason())
                .adjustments(leave.getAdjustments())
                .LeaveStatus(leave.getLeaveStatus())
                .build();*/
        return modelMapper.map(leave,LeaveDto.class);

    }


}
