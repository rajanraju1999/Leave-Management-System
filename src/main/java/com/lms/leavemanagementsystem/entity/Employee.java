package com.lms.leavemanagementsystem.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Employee")
public class Employee {
    @Id
    @Column(name = "employeeId")
    private Long employeeId;

    @Column(name = "employeeName")
    private String employeeName;

    @Column(name = "email")
    private  String email;

    @Column(name = "password")
    private String password;

    @Column(name = "Department")
    private String Department;

    @Column(name = "casualLeaves")
    private String casualLeaves;

    @Column(name = "earnedLeaves")
    private String earnedLeaves;

    @Column(name = "onDuty")
    private String onDuty;

    @Column(name = "permissions")
    private String permissions;

    @Column(name = "lop")
    private String lop;

    @Column(name = "lateComing")
    private String lateComing;

    @Column(name = "number_of_leaves")
    private int numberOfLeaves;

}
