package com.HRM_Project.HRM_Project.service;

import com.HRM_Project.HRM_Project.dto.OfficeLocationRequestDTO;
import com.HRM_Project.HRM_Project.entity.OfficeLocation;
import com.HRM_Project.HRM_Project.repository.OfficeLocationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OfficeLocationService {

    private final OfficeLocationDao officeLocationDao;

    @Autowired
    public OfficeLocationService(OfficeLocationDao officeLocationDao) {
        this.officeLocationDao = officeLocationDao;
    }

    public OfficeLocation createOfficeLocation(OfficeLocationRequestDTO newOfficeLocation){
        OfficeLocation officeLocation = new OfficeLocation();
        officeLocation.setCity(newOfficeLocation.getCity());
        officeLocation.setState(newOfficeLocation.getState());
        officeLocation.setCountryCode(newOfficeLocation.getCountryCode());
        return officeLocationDao.save(officeLocation);
    }

}
