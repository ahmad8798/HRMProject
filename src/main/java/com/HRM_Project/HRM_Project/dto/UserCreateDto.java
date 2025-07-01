package com.HRM_Project.HRM_Project.dto;

import com.HRM_Project.HRM_Project.entity.User;
import com.HRM_Project.HRM_Project.validation.ValidationGroups;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {

    @NotBlank(message = "Username is required", groups = ValidationGroups.NotEmptyCheck.class)
    @Email(message = "Username must be a valid email address", groups = ValidationGroups.PatternCheck.class)
    @Size(max = 100, message = "Username must not exceed 100 characters", groups = ValidationGroups.SizeCheck.class)
    private String username; // This will store the email
    
    @NotBlank(message = "Password is required", groups = ValidationGroups.NotEmptyCheck.class)
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters",
          groups = ValidationGroups.SizeCheck.class)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
             message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit",
             groups = ValidationGroups.PatternCheck.class)
    private String password;
    
    @NotBlank(message = "Role is required", groups = ValidationGroups.NotEmptyCheck.class)
    @Pattern(regexp = "^(ADMIN|USER)$", 
             message = "Role must be either 'ADMIN' or 'USER'",
             groups = ValidationGroups.PatternCheck.class)
    private String role;
    
    @NotNull(message = "Employee ID is required", groups = ValidationGroups.NotEmptyCheck.class)
    @Positive(message = "Employee ID must be positive", groups = ValidationGroups.PatternCheck.class)
    private Integer employeeId;
    

}