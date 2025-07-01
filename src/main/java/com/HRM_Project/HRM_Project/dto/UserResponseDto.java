package com.HRM_Project.HRM_Project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private String role;
    private String roleDisplayName;
    
    // Helper methods for role checking
    public boolean isAdmin() {
        return "HR_ADMIN".equals(role) || "ADMIN".equals(role);
    }
    
    public boolean isUser() {
        return "USER".equals(role);
    }
}