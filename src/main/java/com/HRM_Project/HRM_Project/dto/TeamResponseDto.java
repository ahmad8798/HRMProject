package com.HRM_Project.HRM_Project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamResponseDto {

    private Integer teamId;
    private String name;
    private Integer managerId;
    private String managerName;
}
