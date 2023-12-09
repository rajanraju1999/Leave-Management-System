package com.lms.leavemanagementsystem.service;

import com.lms.leavemanagementsystem.dto.EmployeeDto;
import com.lms.leavemanagementsystem.dto.LeaveDto;
import com.lms.leavemanagementsystem.dto.LeaveDtoApprove;
import com.lms.leavemanagementsystem.entity.Employee;
import com.lms.leavemanagementsystem.entity.Leave;
import com.lms.leavemanagementsystem.exception.CustomException.EmployeeIdNotFoundException;
import com.lms.leavemanagementsystem.exception.CustomException.LeaveIdNotFoundException;
import com.lms.leavemanagementsystem.repository.EmployeeRepository;
import com.lms.leavemanagementsystem.repository.LeaveRepository;
import com.lms.leavemanagementsystem.util.Convert;
import com.lms.leavemanagementsystem.util.leavehandler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lms.leavemanagementsystem.util.leavehandler.LeaveType.CL;
import static com.lms.leavemanagementsystem.util.leavehandler.LeaveType.EL;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    LeaveRepository leaveRepository;

    @Autowired
    Convert convert;

    @Autowired
    CasualLeaveHandler casualLeaveHandler;
    @Autowired
    EarnedLeaveHandler earnedLeaveHandler;
    @Autowired
    OnDutyHandler onDutyHandler;
    @Autowired
    PermissionHandler permissionHandler;

    @Autowired
    LopHandler lopHandler;

    public void createEmployee(EmployeeDto employeeDto) {

        employeeRepository.save(convert.convertToEmployee(employeeDto));

    }

    public List<LeaveDto> getLeavesById (Long id){
        return employeeRepository.findByEmployeeId(id).
                map(e -> e.getLeaveList().stream()
                        .map(convert::convertToLeaveDto)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public void applyLeave(LeaveDto leaveDto) {

        if(employeeRepository.existsById(leaveDto.getEmployeeID())){
            leaveRepository.save(convert.convertToLeave(leaveDto));
        }
        else{
            throw new EmployeeIdNotFoundException();

        }
    }

    public void approveLeave(LeaveDtoApprove leaveDtoApprove) {


        Optional<Leave> optionalLeave = Optional.ofNullable(leaveRepository.findByleaveID(leaveDtoApprove.getLeaveID()));

        optionalLeave.ifPresent(leave -> {
            Double leavesApplied = leave.getLeavesApplied();
            LeaveType leaveType = leave.getLeaveType();
            LeaveHandler leaveHandler = lopHandler;
            switch (leaveType) {
                case CL -> leaveHandler = casualLeaveHandler;
                case EL -> leaveHandler = earnedLeaveHandler;
                case OnDuty -> leaveHandler = onDutyHandler;
                case Permission -> leaveHandler = permissionHandler;
            }
            leaveHandler.deductLeave(leave);

            leave.setLeavesApplied(leavesApplied);
            leave.setLeaveStatus("Approved");
            leaveRepository.save(leave);
        });

// If needed, you can handle the case where the leave is not found
        if (optionalLeave.isEmpty()) {
           throw new LeaveIdNotFoundException();
        }

    }

}
