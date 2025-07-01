package com.HRM_Project.HRM_Project.service;

import com.HRM_Project.HRM_Project.dto.CreateEmployeeWithUserRequestDto;
import com.HRM_Project.HRM_Project.dto.CreateEmployeeWithUserResponseDto;
import com.HRM_Project.HRM_Project.entity.Employee;
import com.HRM_Project.HRM_Project.entity.User;
import org.springframework.stereotype.Service;

/**
 * Service responsible for mapping between DTOs and entities
 * for employee-user operations.
 */
@Service
public class EmployeeUserMappingService {
    
    /**
     * Maps Employee and User entities to CreateEmployeeWithUserResponseDto
     */
    public CreateEmployeeWithUserResponseDto mapToResponseDto(Employee employee, User user) {
        CreateEmployeeWithUserResponseDto responseDto = new CreateEmployeeWithUserResponseDto();
        
        // Employee information
        responseDto.setEmployeeId(employee.getEmployeeId());
        responseDto.setFirstName(employee.getFirstName());
        responseDto.setLastName(employee.getLastName());
        responseDto.setEmail(employee.getEmail());
        responseDto.setInHandSalary(employee.getInHandSalary());
        
        // Office location information
        if (employee.getOfficeLocation() != null) {
            responseDto.setOfficeLocationId(employee.getOfficeLocation().getLocationId());
            responseDto.setOfficeLocationName(
                employee.getOfficeLocation().getCity() + ", " + 
                employee.getOfficeLocation().getState()
            );
        }
        
        // Team information
        if (employee.getTeam() != null) {
            responseDto.setTeamId(employee.getTeam().getTeamId());
            responseDto.setTeamName(employee.getTeam().getName());
        }
        
        // Manager information
        if (employee.getManagerId() != null) {
            responseDto.setManagerId(employee.getManagerId().getEmployeeId());
            responseDto.setManagerName(
                employee.getManagerId().getFirstName() + " " + 
                employee.getManagerId().getLastName()
            );
        }
        
        // User information
        responseDto.setUserId(user.getId());
        responseDto.setUsername(user.getUsername());
        responseDto.setRole(user.getRoleName());
        responseDto.setRoleDisplayName(user.getRoleDisplayName());
        
        return responseDto;
    }
}