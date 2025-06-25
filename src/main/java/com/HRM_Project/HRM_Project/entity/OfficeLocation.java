package com.HRM_Project.HRM_Project.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OfficeLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false,nullable = false)
    private Integer locationId;
    @Column(unique = true, nullable = false)
    private String city;
    @Column( nullable = false)
    private String state;
    @Column( nullable = false)
    private String countryCode;
}

