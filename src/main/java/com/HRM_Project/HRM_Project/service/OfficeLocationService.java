package com.HRM_Project.HRM_Project.service;

import com.HRM_Project.HRM_Project.dto.OfficeLocationRequestDTO;
import com.HRM_Project.HRM_Project.dto.OfficeLocationResponseDTO;
import com.HRM_Project.HRM_Project.entity.OfficeLocation;
import com.HRM_Project.HRM_Project.exception.ResourceNotFoundException;
import com.HRM_Project.HRM_Project.repository.OfficeLocationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class OfficeLocationService {

    private final OfficeLocationDao officeLocationDao;

    @Autowired
    public OfficeLocationService(OfficeLocationDao officeLocationDao) {
        this.officeLocationDao = officeLocationDao;
    }

    public OfficeLocationResponseDTO createOfficeLocation(OfficeLocationRequestDTO newOfficeLocation){
        OfficeLocation officeLocation = new OfficeLocation();
        officeLocation.setCity(newOfficeLocation.getCity());
        officeLocation.setState(newOfficeLocation.getState());
        officeLocation.setCountryCode(newOfficeLocation.getCountryCode());
        OfficeLocation savedOfficeLocation = officeLocationDao.save(officeLocation);
        return convertToDto(savedOfficeLocation);
    }

    public List<OfficeLocationResponseDTO> getAllOfficeLocations(){
        List<OfficeLocation> officeLocations = officeLocationDao.findAll();
        return officeLocations.stream().map(this::convertToDto).collect(Collectors.toList());

    }

    public OfficeLocationResponseDTO getOfficeLocationById(Integer locationId){
        OfficeLocation officeLocation = officeLocationDao.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("OfficeLocation", "id", locationId));
        return convertToDto(officeLocation);
    }

    private OfficeLocationResponseDTO convertToDto(OfficeLocation officeLocation) {
        return new OfficeLocationResponseDTO(officeLocation);
    }

    // Add this method to your OfficeLocationService class
    public OfficeLocation getOfficeLocationEntityById(Integer locationId) {
        return officeLocationDao.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("OfficeLocation", "id", locationId));
    }
}