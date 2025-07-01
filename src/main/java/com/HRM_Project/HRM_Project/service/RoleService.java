package com.HRM_Project.HRM_Project.service;
import com.HRM_Project.HRM_Project.entity.Role;
import com.HRM_Project.HRM_Project.exception.ResourceNotFoundException;
import com.HRM_Project.HRM_Project.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class RoleService {
    
    private final RoleRepository roleRepository;
    
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    
    /**
     * Gets a role by its name
     */
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", name));
    }
    
    /**
     * Gets all roles
     */
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    
    /**
     * Initializes default roles
     */
    @PostConstruct
    public void initializeRoles() {
        // Check if HR_ADMIN role exists
        if (!roleRepository.existsByName("HR_ADMIN")) {
            Role adminRole = new Role("HR_ADMIN", "HR Administrator", "Full system access for HR administrators");
            roleRepository.save(adminRole);
        }
        
        // Check if USER role exists
        if (!roleRepository.existsByName("USER")) {
            Role userRole = new Role("USER", "Regular User", "Basic access for regular employees");
            roleRepository.save(userRole);
        }
        
        // For backward compatibility
        if (!roleRepository.existsByName("ADMIN")) {
            Role legacyAdminRole = new Role("ADMIN", "HR Administrator", "Legacy admin role (maps to HR_ADMIN)");
            roleRepository.save(legacyAdminRole);
        }
    }
    
    /**
     * Maps legacy role names to the new system
     */
    public String normalizeRoleName(String roleName) {
        if (roleName == null) {
            return "USER"; // Default role
        }
        
        // Handle legacy role names
        if (roleName.equalsIgnoreCase("ADMIN")) {
            return "HR_ADMIN";
        }
        
        // Make sure role is uppercase
        return roleName.toUpperCase();
    }
}