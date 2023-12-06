package com.lms.leavemanagementsystem.util.leavehandler;

import com.lms.leavemanagementsystem.entity.Employee;
import com.lms.leavemanagementsystem.entity.Leave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissionHandler implements LeaveHandler  {

    @Autowired
    LopHandler lopHandler;
    @Autowired
    CasualLeaveHandler casualLeaveHandler;
    public void deductLeave(Leave leave){

        Employee employee = leave.getEmployee();

        if (employee.getPermissions() >= leave.getLeavesApplied()) {
            employee.setPermissions(employee.getPermissions() - leave.getLeavesApplied());
        } else {
            leave.setLeavesApplied((leave.getLeavesApplied()-employee.getPermissions())/4);
            casualLeaveHandler.deductLeave(leave);
            employee.setPermissions(0.0);
        }

    }
}
