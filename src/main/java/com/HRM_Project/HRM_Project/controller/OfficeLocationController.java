package com.HRM_Project.HRM_Project.controller;

import com.HRM_Project.HRM_Project.dto.ApiResponse;
import com.HRM_Project.HRM_Project.dto.OfficeLocationRequestDTO;
import com.HRM_Project.HRM_Project.dto.OfficeLocationResponseDTO;
import com.HRM_Project.HRM_Project.entity.OfficeLocation;
import com.HRM_Project.HRM_Project.service.OfficeLocationService;
import com.HRM_Project.HRM_Project.validation.ValidationGroups;
import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/office-locations")
public class OfficeLocationController {

    private final OfficeLocationService officeLocationService;

    @Autowired
    public OfficeLocationController(OfficeLocationService officeLocationService) {
        this.officeLocationService = officeLocationService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllOfficeLocations(){
        List<OfficeLocation> officeLocations = officeLocationService.getAllOfficeLocations();

        if(officeLocations.isEmpty()){
            return new ResponseEntity<>(ApiResponse.error("No office locations found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ApiResponse.success("Office locations retrieved successfully", officeLocations), HttpStatus.OK);
    }

    @GetMapping("{locationId}")
    public ResponseEntity<?> getOfficeLocationById(@PathVariable Integer locationId){
        OfficeLocation officeLocation = officeLocationService.getOfficeLocationById(locationId);
        if(officeLocation == null){
            return new ResponseEntity<>(ApiResponse.error("Office location with id "+locationId+" is not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ApiResponse.success("Office location retrieved successfully", officeLocation), HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<?> createOfficeLocation(@RequestBody @Validated(ValidationSequence.class) OfficeLocationRequestDTO newOfficeLocation) {
        OfficeLocation officeLocation = officeLocationService.createOfficeLocation(newOfficeLocation);
        OfficeLocationResponseDTO officeLocationResponseDTO = new OfficeLocationResponseDTO(officeLocation);
        ApiResponse<OfficeLocationResponseDTO> response = ApiResponse.success("Office location created successfully"
                , officeLocationResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GroupSequence({ValidationGroups.NotEmptyCheck.class,
            ValidationGroups.SizeCheck.class,
            ValidationGroups.PatternCheck.class,
            Default.class})
    public interface ValidationSequence {
    }


}
