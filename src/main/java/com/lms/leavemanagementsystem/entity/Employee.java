package com.lms.leavemanagementsystem.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Employee")
public class Employee implements UserDetails {
    @Id
    @Column(name = "employeeId")
    private Long employeeId;

    @Column(name = "employeeName")
    private String employeeName;

    @Column(name = "email")
    private  String email;

    @Column(name = "password")
    private String password;

    @Column(name = "department")
    private String department;

    @Column(name = "casualLeaves")
    private Double casualLeaves;

    @Column(name = "earnedLeaves")
    private Double earnedLeaves;

    @Column(name = "onDuty")
    private Double onDuty;

    @Column(name = "permissions")
    private Double permissions;

    @Column(name = "lop")
    private Double lop;

    @Column(name = "lateComing")
    private Double lateComing;

    @Column(name = "number_of_leaves")
    private Integer numberOfLeaves;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Leave> leaveList;

    //To add default value
    @PrePersist
    public void prePersist() {
        casualLeaves = (casualLeaves == null) ? 8.0 : casualLeaves;
        earnedLeaves = (earnedLeaves == null) ? 12.0 : earnedLeaves;
        onDuty = (onDuty == null) ? 10.0 : onDuty;
        permissions = (permissions == null) ? 2.0 : permissions;
        lop = (lop == null) ? 0.0 : lop;
        lateComing = (lateComing == null) ? 0.0 : lateComing;
        numberOfLeaves = (numberOfLeaves == null) ? 0: numberOfLeaves;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
