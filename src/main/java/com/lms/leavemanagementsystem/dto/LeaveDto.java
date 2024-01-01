package com.lms.leavemanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.leavemanagementsystem.util.leavehandler.LeaveType;
import com.lms.leavemanagementsystem.validation.EnumValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeaveDto {

   // @NotNull(message = "Employee ID cannot be null")
    private Long employeeID;

    @NotNull(message = "Employee ID cannot be null")
    private String approverEmail;

    @NotBlank(message = "cant not be blank")
    @EnumValid(enumClass = LeaveType.class, message = "Invalid LeaveType value : ")
    private String leaveType;

    @NotBlank(message = "cant not be blank")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}",message = "start date format should be yyyy-mm-dd")
    private String startDate;

    @NotBlank(message = "cant not be blank")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}",message = "end date format should be yyyy-mm-dd")
    private String endDate;

    @NotBlank(message = "cant not be blank")
    private String reason;

    @NotBlank(message = "cant not be blank")
    private String adjustments;

    @NotBlank(message = "cant not be blank")
    @EnumValid(enumClass = HalfDay.class, message = "Invalid HalfDay value : ")
    private String halfDay;

}
