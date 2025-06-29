package com.HRM_Project.HRM_Project.controller;
import com.HRM_Project.HRM_Project.dto.ApiResponse;
import com.HRM_Project.HRM_Project.dto.TeamCreateDto;
import com.HRM_Project.HRM_Project.dto.TeamResponseDto;
import com.HRM_Project.HRM_Project.service.TeamService;
import com.HRM_Project.HRM_Project.validation.ValidationGroups;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/admin/teams")
public class TeamController {

    TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping()
    public ResponseEntity<?> createTeam(@Validated(ValidationGroups.ValidationSequence.class) @RequestBody TeamCreateDto teamCreateDto){
        TeamResponseDto teamResponseDto = teamService.createTeam(teamCreateDto);
        ApiResponse<TeamResponseDto> response = ApiResponse.success("Team created successfully", teamResponseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("{teamId}")
    public ResponseEntity<?> getTeamById(@PathVariable Integer teamId){
        TeamResponseDto team = teamService.getTeamById(teamId);
        return new ResponseEntity<>(ApiResponse.success("Team retrieved successfully", team), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getAllTeams(){
        List<TeamResponseDto> teams = teamService.getAllTeams();
        if(teams.isEmpty()){
            return new ResponseEntity<>(ApiResponse.error("No teams found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ApiResponse.success("Teams retrieved successfully", teams), HttpStatus.OK);
    }

}
