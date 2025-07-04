package com.HRM_Project.HRM_Project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDto {
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
    private String managerName; // Only include manager's name, not the full object
}