package com.HRM_Project.HRM_Project.service;

import com.HRM_Project.HRM_Project.dto.UserResponseDto;
import com.HRM_Project.HRM_Project.entity.Employee;
import com.HRM_Project.HRM_Project.entity.Role;
import com.HRM_Project.HRM_Project.entity.User;
import com.HRM_Project.HRM_Project.exception.ValidationException;
import com.HRM_Project.HRM_Project.repository.EmployeeDao;
import com.HRM_Project.HRM_Project.repository.UserDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserDao userDao;
    private final EmployeeDao employeeDao;
    private final RoleService roleService;

    public UserService(UserDao userDao, EmployeeDao employeeDao, RoleService roleService) {
        this.userDao = userDao;
        this.employeeDao = employeeDao;
        this.roleService = roleService;
    }

    /**
     * Creates a user account for the given employee.
     * This method handles user-specific validations and creation.
     */
    public User createUserForEmployee(Employee employee, String password, String roleName) {
        // Check if user already exists for this employee
        if (userDao.findByEmployee_EmployeeId(employee.getEmployeeId()).isPresent()) {
            throw ValidationException.userAlreadyExists(employee.getEmployeeId());
        }
        
        // Check if username (email) already exists
        if (userDao.findByUsername(employee.getEmail()).isPresent()) {
            throw ValidationException.duplicateUsername(employee.getEmail());
        }
        
        User user = new User();
        user.setUsername(employee.getEmail());
        user.setPassword(password); // Note: You should hash this password

        // Get Role entity for the given role name
        String normalizedRoleName = roleService.normalizeRoleName(roleName);
        Role roleEntity = roleService.getRoleByName(normalizedRoleName);
        user.setRole(roleEntity);

        user.setEmployee(employee);
        
        return userDao.save(user);
    }
    
    // Convert User entity to UserResponseDto
    public UserResponseDto convertToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        
        // Set role string from the Role entity
        if (user.getRole() != null) {
            dto.setRole(user.getRole().getName());
            dto.setRoleDisplayName(user.getRole().getDisplayName());
        }
        
        return dto;
    }
}