package com.HRM_Project.HRM_Project.repository;

import com.HRM_Project.HRM_Project.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamDao extends JpaRepository<Team,Integer> {
}
