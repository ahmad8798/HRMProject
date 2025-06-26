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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin/office-locations")
public class OfficeLocationController {

    private final OfficeLocationService officeLocationService;

    @Autowired
    public OfficeLocationController(OfficeLocationService officeLocationService) {
        this.officeLocationService = officeLocationService;
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
