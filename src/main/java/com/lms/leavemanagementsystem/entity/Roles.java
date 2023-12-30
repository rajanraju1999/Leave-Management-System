package com.lms.leavemanagementsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Roles {

    @Id
    @Column(name = "roleID")
    Long roleID;

    @Column(name = "roleName")
    String roleName;

}
