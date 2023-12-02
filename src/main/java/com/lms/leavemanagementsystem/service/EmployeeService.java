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

import java.math.BigDecimal;
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

    public void approveLeave( LeaveDto leaveDto){

      Leave leave = leaveRepository.findByleaveID( leaveDto.getLeaveID());
      Employee employee = leave.getEmployee();
      if("CL".equals(leave.getLeaveType()) && employee.getCasualLeaves() > 0){
          employee.setCasualLeaves(employee.getCasualLeaves() - leave.getLeavesApplied());
      } else if (("EL".equals(leave.getLeaveType())||"CL".equals(leave.getLeaveType()) && employee.getCasualLeaves() < 0) && employee.getEarnedLeaves()  > 0 ) {
          employee.setEarnedLeaves(employee.getEarnedLeaves() - leave.getLeavesApplied());
      }else if ("OnDuty".equals(leave.getLeaveType()) && employee.getOnDuty()  > 0) {
          employee.setOnDuty(employee.getOnDuty() - leave.getLeavesApplied());
      }else if ("Permission".equals(leave.getLeaveType()) && employee.getPermissions()  > 0) {
          employee.setPermissions(employee.getPermissions() - leave.getLeavesApplied());
      }else {
          employee.setLop(employee.getLop() + leave.getLeavesApplied());
      }
      leave.setLeaveStatus("Approved");
      leave.setReason(leaveDto.getReason());
      leaveRepository.save(leave);

    }


 /* public List<LeaveDto> getLeavesById(Long id) {
       Optional<Employee> employee = employeeRepository.findByEmployeeId(id);
      List<Leave> list2 = employee.get().getLeaveList();
       List<LeaveDto> list1 = new ArrayList<>();
       for (int i = 0; i< list2 .size(); i++)
           list1.add(convert.convertToLeaveDto(list2 .get(i)));

       return list1;
   }*/

    public List<LeaveDto> getLeavesById(Long id) {
        return  employeeRepository.findByEmployeeId(id).
                map(e->e.getLeaveList().stream()
                        .map(convert::convertToLeaveDto)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }


}
