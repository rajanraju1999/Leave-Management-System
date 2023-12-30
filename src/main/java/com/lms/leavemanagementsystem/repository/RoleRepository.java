package com.lms.leavemanagementsystem.repository;



import com.lms.leavemanagementsystem.dto.RoleDto;
import com.lms.leavemanagementsystem.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Roles,Long> {
    Optional<Roles> findByRoleName(String name);

}