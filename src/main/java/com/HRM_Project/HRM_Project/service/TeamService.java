package com.HRM_Project.HRM_Project.service;

import com.HRM_Project.HRM_Project.dto.TeamCreateDto;
import com.HRM_Project.HRM_Project.dto.TeamResponseDto;
import com.HRM_Project.HRM_Project.entity.Employee;
import com.HRM_Project.HRM_Project.entity.Team;
import com.HRM_Project.HRM_Project.exception.ResourceNotFoundException;
import com.HRM_Project.HRM_Project.repository.EmployeeDao;
import com.HRM_Project.HRM_Project.repository.TeamDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    TeamDao teamDao;
    EmployeeDao employeeDao;

    public TeamService(TeamDao teamDao, EmployeeDao employeeDao) {
        this.teamDao = teamDao;
        this.employeeDao = employeeDao;
    }


    public TeamResponseDto createTeam(TeamCreateDto teamCreateDto){
        Employee manager = employeeDao.findById(teamCreateDto.getManagerId()).orElseThrow(()->new ResourceNotFoundException("Employee", "id", teamCreateDto.getManagerId()));

        Team team = new Team();
        team.setName(teamCreateDto.getName());
        team.setManager(manager);
        Team savedTeam = teamDao.save(team);
        return  convertToDto(savedTeam);

    }

    public TeamResponseDto getTeamById(Integer teamId){
        Team team = teamDao.findById(teamId).orElseThrow(()->new ResourceNotFoundException("Team", "id", teamId));
        return convertToDto(team);
    }

    public List<TeamResponseDto> getAllTeams(){
        List<Team> teams = teamDao.findAll();
        return teams.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    public TeamResponseDto convertToDto(Team team) {
        TeamResponseDto dto = new TeamResponseDto();
        dto.setTeamId(team.getTeamId());
        dto.setName(team.getName());

        if (team.getManager() != null) {
            dto.setManagerId(team.getManager().getEmployeeId());
            dto.setManagerName(team.getManager().getFirstName() + " " + team.getManager().getLastName());
        }

        return dto;
    }

}
