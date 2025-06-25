package com.HRM_Project.HRM_Project.dto;

import com.HRM_Project.HRM_Project.validation.ValidationGroups;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeLocationRequestDTO {

    @NotNull(message = "City cannot be null",groups = ValidationGroups.NotEmptyCheck.class)
    @NotEmpty(message = "City cannot be empty",groups = ValidationGroups.NotEmptyCheck.class)
    @Size(min = 2, max = 100, message = "City name must be between 2 and 100 characters",groups = ValidationGroups.SizeCheck.class)
    @Pattern(regexp = "^[a-zA-Z\\s\\-'.]+$", message = "City name should contain only letters, spaces, hyphens, apostrophes, and periods",
    groups = ValidationGroups.PatternCheck.class)
    private String city;


    @NotNull(message = "State cannot be null",groups = ValidationGroups.NotEmptyCheck.class)
    @NotEmpty(message = "State cannot be empty",groups = ValidationGroups.NotEmptyCheck.class)
    @Size(min = 2, max = 100, message = "State name must be between 2 and 100 characters",groups = ValidationGroups.SizeCheck.class)
    @Pattern(regexp = "^[a-zA-Z\\s\\-'.]+$", message = "State should contain only letters, spaces, hyphens, apostrophes, and periods"
    ,groups = ValidationGroups.PatternCheck.class)
    private String state;

    @NotNull(message = "Country code cannot be null",groups = ValidationGroups.NotEmptyCheck.class)
    @NotEmpty(message = "Country code cannot be empty",groups = ValidationGroups.NotEmptyCheck.class)
    @Size(min = 2, max = 3, message = "Country code must be 2 or 3 characters",groups = ValidationGroups.SizeCheck.class)
    @Pattern(regexp = "^[A-Z]+$", message = "Country code should be in uppercase letters only"
    ,groups = ValidationGroups.PatternCheck.class)
    private String countryCode;


}



