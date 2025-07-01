package com.HRM_Project.HRM_Project.service;

import com.HRM_Project.HRM_Project.dto.CreateEmployeeWithUserRequestDto;
import com.HRM_Project.HRM_Project.dto.CreateEmployeeWithUserResponseDto;
import com.HRM_Project.HRM_Project.entity.Employee;
import com.HRM_Project.HRM_Project.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeUserOrchestrationService {
    
    private final EmployeeService employeeService;
    private final UserService userService;
    
    public EmployeeUserOrchestrationService(EmployeeService employeeService, UserService userService) {
        this.employeeService = employeeService;
        this.userService = userService;
    }
    
    /**
     * Creates an employee and a user account in a single transaction.
     */
    @Transactional
    public CreateEmployeeWithUserResponseDto createEmployeeWithUser(CreateEmployeeWithUserRequestDto request) {
        // First create the employee
        Employee employee = employeeService.createEmployeeFromRequest(request);
        
        // Then create the user
        User user = userService.createUserForEmployee(employee, request.getPassword(), request.getRole());
        
        // Return combined response
        return mapToResponseDto(employee, user);
    }
    
    /**
     * Maps the employee and user entities to a response DTO.
     */
    private CreateEmployeeWithUserResponseDto mapToResponseDto(Employee employee, User user) {
        CreateEmployeeWithUserResponseDto responseDto = new CreateEmployeeWithUserResponseDto();
        
        // Employee information
        responseDto.setEmployeeId(employee.getEmployeeId());
        responseDto.setFirstName(employee.getFirstName());
        responseDto.setLastName(employee.getLastName());
        responseDto.setEmail(employee.getEmail());
        responseDto.setInHandSalary(employee.getInHandSalary());
        
        if (employee.getOfficeLocation() != null) {
            responseDto.setOfficeLocationId(employee.getOfficeLocation().getLocationId());
            responseDto.setOfficeLocationName(employee.getOfficeLocation().getCity() + ", " + 
                                             employee.getOfficeLocation().getState());
        }
        
        if (employee.getTeam() != null) {
            responseDto.setTeamId(employee.getTeam().getTeamId());
            responseDto.setTeamName(employee.getTeam().getName());
        }
        
        if (employee.getManagerId() != null) {
            responseDto.setManagerId(employee.getManagerId().getEmployeeId());
            responseDto.setManagerName(employee.getManagerId().getFirstName() + " " + 
                                      employee.getManagerId().getLastName());
        }
        
        // User information
        responseDto.setUserId(user.getId());
        responseDto.setUsername(user.getUsername());

        // Set role information
        responseDto.setRole(user.getRoleName());
        responseDto.setRoleDisplayName(user.getRoleDisplayName());
        
        return responseDto;
    }
}