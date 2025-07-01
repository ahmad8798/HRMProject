package com.HRM_Project.HRM_Project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_username", columnNames = "username"),
                @UniqueConstraint(name = "uk_user_employee", columnNames = "employee_id")
        }
)
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String username; // This will store the email

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    // Replace string role with ManyToOne relationship
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role", nullable = false)
    private Role role;

    @OneToOne
    @JoinColumn(name = "employee_id", unique = true)
    private Employee employee;

    public void setEmailAsUsername(String email) {
        this.username = email;
    }

    // Helper methods for role checking
    public boolean isAdmin() {
        if (role != null) {
            String roleName = role.getName();
            return "HR_ADMIN".equals(roleName) || "ADMIN".equals(roleName);
        }
        return false;
    }

    public boolean isUser() {
        if (role != null) {
            return "USER".equals(role.getName());
        }
        return false;
    }

    // Get role name as string
    public String getRoleName() {
        if (role != null) {
            return role.getName();
        }
        return null;
    }

    // Get role display name
    public String getRoleDisplayName() {
        if (role != null) {
            return role.getDisplayName();
        }
        return null;
    }
}