package com.HRM_Project.HRM_Project.service;
import com.HRM_Project.HRM_Project.dto.CreateEmployeeWithUserRequestDto;
import com.HRM_Project.HRM_Project.dto.EmployeeCreateDto;
import com.HRM_Project.HRM_Project.dto.EmployeeResponseDto;
import com.HRM_Project.HRM_Project.entity.Employee;
import com.HRM_Project.HRM_Project.entity.OfficeLocation;
import com.HRM_Project.HRM_Project.entity.Team;
import com.HRM_Project.HRM_Project.exception.ResourceNotFoundException;
import com.HRM_Project.HRM_Project.exception.ValidationException;
import com.HRM_Project.HRM_Project.repository.EmployeeDao;
import com.HRM_Project.HRM_Project.repository.TeamDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    EmployeeDao employeeDao;
    OfficeLocationService officeLocationService;
    TeamDao teamDao;

    public EmployeeService(EmployeeDao employeeDao, OfficeLocationService officeLocationService, TeamDao teamDao) {
        this.employeeDao = employeeDao;
        this.officeLocationService = officeLocationService;
        this.teamDao = teamDao;
    }

    public EmployeeResponseDto createEmployee(EmployeeCreateDto employeeCreateDto){
        Employee employee = new Employee();
        employee.setFirstName(employeeCreateDto.getFirstName());
        employee.setLastName(employeeCreateDto.getLastName());
        employee.setEmail(employeeCreateDto.getEmail());
        employee.setInHandSalary(employeeCreateDto.getInHandSalary());

        if(employeeCreateDto.getOfficeLocationId()!=null){
            // Check if office location exists by trying to get it
            OfficeLocation officeLocation = officeLocationService.getOfficeLocationEntityById(employeeCreateDto.getOfficeLocationId());
            // The method will throw an exception if not found, so no need for null check
            employee.setOfficeLocation(officeLocation);
        }

        if(employeeCreateDto.getTeamId()!=null){
            Team team = teamDao.findById(employeeCreateDto.getTeamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Team", "id", employeeCreateDto.getTeamId()));
            employee.setTeam(team);
        }

        if(employeeCreateDto.getManagerId()!=null){
            Employee manager = employeeDao.findById(employeeCreateDto.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager", "id", employeeCreateDto.getManagerId()));
            employee.setManagerId(manager);
        }

        Employee savedEmployee = employeeDao.save(employee);
        return convertToDto(savedEmployee);
    }

    public List<EmployeeResponseDto> getAllEmployees(){
        List<Employee> employees = employeeDao.findAll();
        return employees.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public EmployeeResponseDto getEmployeeById(Integer employeeId){
        Employee employee = employeeDao.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        return convertToDto(employee);
    }

    public EmployeeResponseDto convertToDto(Employee employee) {
        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setInHandSalary(employee.getInHandSalary());
        
        if (employee.getOfficeLocation() != null) {
            dto.setOfficeLocationId(employee.getOfficeLocation().getLocationId());
            dto.setOfficeLocationName(employee.getOfficeLocation().getCity() + ", " + employee.getOfficeLocation().getState());
        }
        
        if (employee.getTeam() != null) {
            dto.setTeamId(employee.getTeam().getTeamId());
            dto.setTeamName(employee.getTeam().getName());
        }
        
        if (employee.getManagerId() != null) {
            dto.setManagerId(employee.getManagerId().getEmployeeId());
            dto.setManagerName(employee.getManagerId().getFirstName() + " " + employee.getManagerId().getLastName());
        }
        
        return dto;
    }

// Add this method to your existing EmployeeService class

    /**
     * Creates an employee from the given request DTO.
     * This method handles all employee-specific validations and entity creation.
     */
    // Update the createEmployeeFromRequest method
public Employee createEmployeeFromRequest(CreateEmployeeWithUserRequestDto request) {
    // Validate email uniqueness
    if (employeeDao.findByEmail(request.getEmail()) != null) {
        throw ValidationException.duplicateEmail(request.getEmail());
    }
    
    Employee employee = new Employee();
    employee.setFirstName(request.getFirstName());
    employee.setLastName(request.getLastName());
    employee.setEmail(request.getEmail());
    employee.setInHandSalary(request.getInHandSalary());
    
    // Handle office location
    if (request.getOfficeLocationId() != null) {
        OfficeLocation officeLocation = officeLocationService.getOfficeLocationEntityById(request.getOfficeLocationId());
        employee.setOfficeLocation(officeLocation);
    }
    
    // Handle team
    if (request.getTeamId() != null) {
        Team team = teamDao.findById(request.getTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team", "id", request.getTeamId()));
        employee.setTeam(team);
    }
    
    // Handle manager
    if (request.getManagerId() != null) {
        Employee manager = employeeDao.findById(request.getManagerId())
                .orElseThrow(() -> new ResourceNotFoundException("Manager", "id", request.getManagerId()));
        employee.setManagerId(manager);
    }
    
    return employeeDao.save(employee);
}
}