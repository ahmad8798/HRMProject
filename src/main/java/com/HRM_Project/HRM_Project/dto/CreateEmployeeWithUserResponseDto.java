package com.HRM_Project.HRM_Project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeWithUserResponseDto {
    
    // Employee information
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private Integer inHandSalary;
    private Integer officeLocationId;
    private String officeLocationName;
    private Integer teamId;
    private String teamName;
    private Integer managerId;
    private String managerName;
    
    // User information
    private Long userId;
    private String username;
    private String role;
    private String roleDisplayName;
}