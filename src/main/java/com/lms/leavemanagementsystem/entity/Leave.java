package com.lms.leavemanagementsystem.entity;

import com.lms.leavemanagementsystem.util.leavehandler.LeaveType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Leaves")
public class Leave {

    @Id
    @Column(name = "Leave_id")
    private String leaveID;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="employeeId", nullable=false,referencedColumnName = "employeeId")
    private Employee employee;

    @Column(name = "approverEmail")
    private String approverEmail;

    @Column(name = "leaveType")
    private LeaveType leaveType;

    @Column(name = "leavesApplied")
    private Double leavesApplied;

    @Column(name = "datePosted")
    private LocalDate datePosted;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "shift")
    private LocalDate shift;

    @Column(name = "reason")
    private String reason;

    @Column(name = "adjustments")
    private String adjustments;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "leaveStatus")
    private String leaveStatus;
    @PrePersist
    public void generateLeaveID() {
        if (this.employee != null && this.employee.getEmployeeId() != null) {
            int numberOfLeaves = this.employee.getNumberOfLeaves();
            this.leaveID = "L"+ "_" + this.employee.getEmployeeId() + "_" + (numberOfLeaves + 1);
            this.employee.setNumberOfLeaves(numberOfLeaves + 1);
        } else {
            // Handle the case where employee or emp_id is null
            // You might throw an exception, log an error, or handle it in another way based on your requirements.
        }
    }

}
