package com.HRM_Project.HRM_Project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false,nullable = false)
    private Integer employeeId;

    private  String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "location_id" , nullable = false)
    private OfficeLocation officeLocation;

    @ManyToOne
    @JoinColumn(name="team_id")
    private  Team team;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee managerId;

    private Integer inHandSalary;
}
