package com.HRM_Project.HRM_Project.repository;

import com.HRM_Project.HRM_Project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, Integer> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmployee_EmployeeId(Integer employeeId);
}