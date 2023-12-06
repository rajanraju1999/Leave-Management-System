package com.lms.leavemanagementsystem.util.leavehandler;

import com.lms.leavemanagementsystem.entity.Employee;
import com.lms.leavemanagementsystem.entity.Leave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OnDutyHandler implements LeaveHandler  {

    @Autowired
    LopHandler lopHandler;
    public void deductLeave(Leave leave){

        Employee employee = leave.getEmployee();

        if (employee.getOnDuty()>= leave.getLeavesApplied()) {
            employee.setEarnedLeaves(employee.getOnDuty() - leave.getLeavesApplied());
        } else {
            leave.setLeavesApplied((long) (leave.getLeavesApplied()-employee.getOnDuty()));
            lopHandler.deductLeave(leave);
            employee.setEarnedLeaves(0.0);
        }

    }
}
