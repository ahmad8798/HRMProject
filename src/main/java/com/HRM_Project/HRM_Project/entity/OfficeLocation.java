package com.HRM_Project.HRM_Project.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "office_location",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_office_location_city",columnNames = "city")
        }
)
public class OfficeLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false,nullable = false)
    private Integer locationId;
    @Column(nullable = false)
    private String city;
    @Column( nullable = false)
    private String state;
    @Column( nullable = false)
    private String countryCode;
}

