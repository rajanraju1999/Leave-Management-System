package com.lms.leavemanagementsystem.util.leavehandler;

import com.lms.leavemanagementsystem.entity.Employee;
import com.lms.leavemanagementsystem.entity.Leave;
import org.springframework.stereotype.Component;

@Component
public class LopHandler implements LeaveHandler  {


    public void deductLeave(Leave leave){

        Employee employee = leave.getEmployee();

            employee.setLop(employee.getLop() + leave.getLeavesApplied());


    }
}
