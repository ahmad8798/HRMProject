package com.HRM_Project.HRM_Project.repository;

import com.HRM_Project.HRM_Project.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDao extends JpaRepository<Employee,Integer>{


    public Employee findByEmail(String email);
}
