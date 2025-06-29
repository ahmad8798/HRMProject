package com.HRM_Project.HRM_Project.controller;

import com.HRM_Project.HRM_Project.dto.ApiResponse;
import com.HRM_Project.HRM_Project.dto.EmployeeCreateDto;
import com.HRM_Project.HRM_Project.dto.EmployeeResponseDto;
import com.HRM_Project.HRM_Project.entity.Employee;
import com.HRM_Project.HRM_Project.service.EmployeeService;
import com.HRM_Project.HRM_Project.validation.ValidationGroups;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/employees")
public class EmployeeController {

    EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping()
    public ResponseEntity<?> createEmployee(@Validated(ValidationGroups.ValidationSequence.class) @RequestBody EmployeeCreateDto employeeCreateDto){
        EmployeeResponseDto responseDto = employeeService.createEmployee(employeeCreateDto);
        ApiResponse<EmployeeResponseDto> response = ApiResponse.success("Employee created successfully", responseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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