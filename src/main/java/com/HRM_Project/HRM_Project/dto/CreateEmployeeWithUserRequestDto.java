package com.HRM_Project.HRM_Project.dto;

import com.HRM_Project.HRM_Project.validation.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeWithUserRequestDto {

    @NotBlank(message = "First name is required", groups = ValidationGroups.NotEmptyCheck.class)
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters", 
          groups = ValidationGroups.SizeCheck.class)
    private String firstName;

    @NotBlank(message = "Last name is required", groups = ValidationGroups.NotEmptyCheck.class)
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters",
          groups = ValidationGroups.SizeCheck.class)
    private String lastName;

    @NotBlank(message = "Email is required", groups = ValidationGroups.NotEmptyCheck.class)
    @Email(message = "Please provide a valid email address", groups = ValidationGroups.PatternCheck.class)
    private String email;

    @NotNull(message = "Office location is required", groups = ValidationGroups.NotEmptyCheck.class)
    private Integer officeLocationId;

    // Optional fields
    private Integer teamId;
    private Integer managerId;

    @NotNull(message = "Salary is required", groups = ValidationGroups.NotEmptyCheck.class)
    @Positive(message = "Salary must be a positive number", groups = ValidationGroups.PatternCheck.class)
    private Integer inHandSalary;
    
    @NotBlank(message = "Password is required", groups = ValidationGroups.NotEmptyCheck.class)
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters",
          groups = ValidationGroups.SizeCheck.class)
    private String password;
    
    @NotBlank(message = "Role is required", groups = ValidationGroups.NotEmptyCheck.class)
    @Pattern(regexp = "^(HR_ADMIN|ADMIN|USER)$", 
             message = "Role must be one of: 'HR_ADMIN', 'ADMIN', or 'USER'",
             groups = ValidationGroups.PatternCheck.class)
    private String role;
}