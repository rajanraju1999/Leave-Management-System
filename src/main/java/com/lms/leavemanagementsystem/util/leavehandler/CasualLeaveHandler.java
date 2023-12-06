package com.lms.leavemanagementsystem.util.leavehandler;

import com.lms.leavemanagementsystem.entity.Employee;
import com.lms.leavemanagementsystem.entity.Leave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CasualLeaveHandler implements LeaveHandler  {

    @Autowired
    LopHandler lopHandler;
    @Autowired
    EarnedLeaveHandler earnedLeaveHandler;

     public void deductLeave(Leave leave){

         Employee employee = leave.getEmployee();



         if (employee.getCasualLeaves() >= leave.getLeavesApplied() && leave.getLeavesApplied() <= 4) {
             employee.setCasualLeaves(employee.getCasualLeaves() - leave.getLeavesApplied());
         } else {

             if(employee.getCasualLeaves()<=4)
             {
                 leave.setLeavesApplied((leave.getLeavesApplied()-employee.getCasualLeaves()));
                 earnedLeaveHandler.deductLeave(leave);
                 employee.setCasualLeaves(0.0);

             }else {

                 leave.setLeavesApplied((leave.getLeavesApplied()-4));
                 earnedLeaveHandler.deductLeave(leave);
                 employee.setCasualLeaves(employee.getCasualLeaves()-4);

             }
         }

//         if (employee.getCasualLeaves() >= leave.getLeavesApplied()) {
//             employee.setCasualLeaves(employee.getCasualLeaves() - leave.getLeavesApplied());
//         } else {
//             leave.setLeavesApplied((long) (leave.getLeavesApplied()-employee.getCasualLeaves()));
//             earnedLeaveHandler.deductLeave(leave);
//             employee.setCasualLeaves(0.0);
//         }

     }

}
