package com.lms.leavemanagementsystem.service;

import com.lms.leavemanagementsystem.dto.EmployeeDto;
import com.lms.leavemanagementsystem.dto.LeaveDto;
import com.lms.leavemanagementsystem.dto.LeaveDtoApprove;
import com.lms.leavemanagementsystem.entity.Employee;
import com.lms.leavemanagementsystem.entity.Leave;
import com.lms.leavemanagementsystem.entity.Roles;
import com.lms.leavemanagementsystem.exception.CustomException.EmployeeIdNotFoundException;
import com.lms.leavemanagementsystem.exception.CustomException.UsernamePasswordException;
import com.lms.leavemanagementsystem.exception.CustomException.LeaveApprovedException;
import com.lms.leavemanagementsystem.exception.CustomException.LeaveIdNotFoundException;
import com.lms.leavemanagementsystem.repository.EmployeeRepository;
import com.lms.leavemanagementsystem.repository.LeaveRepository;
import com.lms.leavemanagementsystem.util.Convert;
import com.lms.leavemanagementsystem.util.leavehandler.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.math.BigDecimal;
import java.util.*;
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

    public List<LeaveDto> getLeavesByEmail (String email){
        try {
            Employee employee = employeeRepository.findByEmail(email);
            if (employee != null) {
                return employee.getLeaveList().stream()
                        .map(convert::convertToLeaveDto)
                        .collect(Collectors.toList());
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {// Handle the exception as needed
            throw new RuntimeException("Error retrieving leaves for email: " + email, e);
        }
    }

    public List<LeaveDto> getLeavesToApprove (String email){
        try {
                return leaveRepository.findByApproverEmail(email).stream()
                        .filter(x-> Objects.equals(x.getLeaveStatus(), "InProcess"))
                        .map(convert::convertToLeaveDto)
                        .collect(Collectors.toList());

        } catch (Exception e) {// Handle the exception as needed
            throw new RuntimeException("Error retrieving leaves for email: " + email, e);
        }
    }




    public String applyLeave(LeaveDto leaveDto) {
        Leave leave = convert.convertToLeave(leaveDto);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (!Objects.equals(leave.getApproverEmail(),userDetails.getUsername())) {
            leaveRepository.save(leave);
            return leave.getLeavesApplied() + " leaves applied with leave type " + leave.getLeaveType();
        }else
        {
           throw new LeaveApprovedException("you can't be your own approver");
        }

    }

    @Transactional
    public void approveLeave(LeaveDtoApprove leaveDtoApprove) {


      Optional<Leave> Leave = Optional.ofNullable(leaveRepository.findByleaveID(leaveDtoApprove.getLeaveID()));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Leave.ifPresent(leave -> {
            if (Objects.equals(leave.getApproverEmail() ,userDetails.getUsername()) && Objects.equals(leave.getLeaveStatus(), "InProcess")){
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
            } else {
                String errorMessage = Objects.equals(leave.getApproverEmail(), userDetails.getUsername()) ?
                        "Leave with leave Id " + leave.getLeaveID() + " is already approved" :
                        "You can't approve this leave as you are not the approver";

                throw new LeaveApprovedException(errorMessage);
            }
        });
        Leave.orElseThrow(() -> new LeaveIdNotFoundException("Leave with ID " + leaveDtoApprove.getLeaveID() + " not found"));

    }

//    public void checkUserRole(String requiredRole) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            // User is not authenticated, handle accordingly
//            throw new JwtException("User is not authenticated");
//        }
//
//        // Check if the user has the required role
//        boolean userHasRole = authentication.getAuthorities().stream()
//                .anyMatch(authority -> authority.getAuthority().equals(requiredRole));
//
//        if (!userHasRole) {
//            throw new JwtException("User does not have the required role: " + requiredRole);
//        }
//    }

}
