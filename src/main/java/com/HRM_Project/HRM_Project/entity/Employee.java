package com.HRM_Project.HRM_Project.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee",
        uniqueConstraints = {
        @UniqueConstraint(name = "uk_employee_email",columnNames = "email"),

        }
)
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
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "teamId")
    @JsonIdentityReference(alwaysAsId = true)
    private  Team team;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "employeeId")
    @JsonIdentityReference(alwaysAsId = true)
    private Employee managerId;

    @Column(nullable = false)
    private Integer inHandSalary;
}
