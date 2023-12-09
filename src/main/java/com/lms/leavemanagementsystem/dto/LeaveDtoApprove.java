package com.lms.leavemanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeaveDtoApprove {
    @NotBlank(message = "cant not be blank")
    @Pattern(regexp = "^L_\\d+_\\d+$",message = "send in right format L_EmployeeNo_LeaveNo")
    private String leaveID;

    private String remarks;
}
