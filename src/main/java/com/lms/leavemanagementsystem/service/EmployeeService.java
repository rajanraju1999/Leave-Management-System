package com.lms.leavemanagementsystem.service;

import com.lms.leavemanagementsystem.dto.EmployeeDto;
import com.lms.leavemanagementsystem.dto.LeaveDto;
import com.lms.leavemanagementsystem.entity.Employee;
import com.lms.leavemanagementsystem.entity.Leave;
import com.lms.leavemanagementsystem.repository.EmployeeRepository;
import com.lms.leavemanagementsystem.repository.LeaveRepository;
import com.lms.leavemanagementsystem.util.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    LeaveRepository leaveRepository;

    @Autowired
    Convert convert;

    public void createEmployee(EmployeeDto employeeDto) {

        employeeRepository.save(convert.convertToEmployee(employeeDto));

    }


    public void applyLeave(LeaveDto leaveDto){
        leaveRepository.save(convert.convertToLeave(leaveDto));
    }


  /* public List<LeaveDto> getLeavesById(Long id) {
       Optional<Employee> employee = employeeRepository.findByEmployeeId(id);
      ArrayList<LeaveDto> list2 = new ArrayList<>()
       List<Leave> list1 =leaveRepository.findByEmployee(employee.get())
       for (int i=0;i< list1.size();i++)
           list2.add(convert.convertToLeaveDto(list1.get(i)));

       return list2;
   }*/

    public List<LeaveDto> getLeavesById(Long id) {
        return   employeeRepository.findByEmployeeId(id)
                        .map(employee -> leaveRepository.findByEmployee(employee)
                                .stream()
                                .map(convert::convertToLeaveDto)
                                .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

}
