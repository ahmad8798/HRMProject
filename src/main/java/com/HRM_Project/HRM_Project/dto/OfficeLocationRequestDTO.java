package com.HRM_Project.HRM_Project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeLocationRequestDTO {

    private String city;
    private String state;
    private String countryCode;

}
