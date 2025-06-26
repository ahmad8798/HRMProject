package com.HRM_Project.HRM_Project.dto;

import com.HRM_Project.HRM_Project.entity.OfficeLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeLocationResponseDTO {

    private Integer locationId;
    private String city;
    private String state;
    private String countryCode;

    public OfficeLocationResponseDTO(OfficeLocation officeLocation){
        this.locationId = officeLocation.getLocationId();
        this.city = officeLocation.getCity();
        this.state = officeLocation.getState();
        this.countryCode = officeLocation.getCountryCode();
    }

}
