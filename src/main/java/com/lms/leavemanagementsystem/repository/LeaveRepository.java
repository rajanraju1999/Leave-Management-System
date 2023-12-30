package com.lms.leavemanagementsystem.repository;

import com.lms.leavemanagementsystem.dto.LeaveDto;
import com.lms.leavemanagementsystem.entity.Employee;
import com.lms.leavemanagementsystem.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LeaveRepository extends JpaRepository<Leave,String> {
    List<Leave> findByEmployee(Employee employee);

    Leave findByleaveID(String leaveID);
}
