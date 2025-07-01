package com.HRM_Project.HRM_Project.controller;

import com.HRM_Project.HRM_Project.dto.*;
import com.HRM_Project.HRM_Project.entity.Employee;
import com.HRM_Project.HRM_Project.service.EmployeeService;
import com.HRM_Project.HRM_Project.service.EmployeeUserOrchestrationService;
import com.HRM_Project.HRM_Project.validation.ValidationGroups;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeUserOrchestrationService orchestrationService;
    public EmployeeController(EmployeeService employeeService, EmployeeUserOrchestrationService orchestrationService) {
        this.employeeService = employeeService;
        this.orchestrationService = orchestrationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateEmployeeWithUserResponseDto>> createEmployeeWithUser(
            @Validated(ValidationGroups.ValidationSequence.class)
            @RequestBody CreateEmployeeWithUserRequestDto request) {

        CreateEmployeeWithUserResponseDto response = orchestrationService.createEmployeeWithUser(request);
        ApiResponse<CreateEmployeeWithUserResponseDto> apiResponse =
                ApiResponse.success("Employee and user account created successfully", response);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Integer employeeId){
        EmployeeResponseDto responseDto = employeeService.getEmployeeById(employeeId);
        return new ResponseEntity<>(ApiResponse.success("Employee retrieved successfully", responseDto), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getAllEmployees(){
        List<EmployeeResponseDto> employeesResponseDtos =employeeService.getAllEmployees();
        if(employeesResponseDtos.isEmpty()){
            return new ResponseEntity<>(ApiResponse.error("No employees found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ApiResponse.success("Employees retrieved successfully", employeesResponseDtos), HttpStatus.OK);
    }


}