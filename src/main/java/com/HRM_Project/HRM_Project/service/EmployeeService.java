package com.HRM_Project.HRM_Project.service;

import com.HRM_Project.HRM_Project.dto.EmployeeCreateDto;
import com.HRM_Project.HRM_Project.dto.EmployeeResponseDto;
import com.HRM_Project.HRM_Project.dto.OfficeLocationResponseDTO;
import com.HRM_Project.HRM_Project.entity.Employee;
import com.HRM_Project.HRM_Project.entity.OfficeLocation;
import com.HRM_Project.HRM_Project.entity.Team;
import com.HRM_Project.HRM_Project.exception.ResourceNotFoundException;
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
}