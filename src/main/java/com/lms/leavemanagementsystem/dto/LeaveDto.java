package com.lms.leavemanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.leavemanagementsystem.entity.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeaveDto {

    private String leaveID;

    private Long employeeID;


    private String leaveType;


    private String startDate;


    private String endDate;


    private String reason;


    private String adjustments;


    private String remarks;


}
