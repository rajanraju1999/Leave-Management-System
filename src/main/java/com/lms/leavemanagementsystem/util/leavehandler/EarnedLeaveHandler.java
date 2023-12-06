package com.lms.leavemanagementsystem.util.leavehandler;

import com.lms.leavemanagementsystem.entity.Employee;
import com.lms.leavemanagementsystem.entity.Leave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EarnedLeaveHandler implements LeaveHandler {

    @Autowired
    LopHandler lopHandler;
    public void deductLeave(Leave leave){

        Employee employee = leave.getEmployee();

        if (employee.getEarnedLeaves() >= leave.getLeavesApplied()) {
            employee.setEarnedLeaves(employee.getEarnedLeaves() - leave.getLeavesApplied());
        } else {
            leave.setLeavesApplied((long) (leave.getLeavesApplied()-employee.getEarnedLeaves()));
            lopHandler.deductLeave(leave);
            employee.setEarnedLeaves(0.0);
        }

    }
}
