package com.HRM_Project.HRM_Project.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCreateDto {
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters") 
    private String lastName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;
    
    @NotNull(message = "Office location is required")
    private Integer officeLocationId;
    
    // Optional fields
    private Integer teamId;
    
    // Added manager ID field
    private Integer managerId;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be a positive number")
    private Integer inHandSalary;
}