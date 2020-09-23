package com.playerteamservice.controller;

import com.model.PlayerResponse;
import com.model.PlayerTeamDataResponse;
import com.model.TeamPlayersResponse;
import com.model.TeamResponse;
import com.playerteamservice.dto.*;
import com.playerteamservice.exception.EntityNotFoundException;
import com.playerteamservice.service.PlayerCRUDService;
import com.playerteamservice.service.PlayerTeamDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/player")
@AllArgsConstructor
@Getter
@Api(tags = "Player based operations.")
public class PlayerController extends GenericCRUDController<PlayerResponse, PlayerRequest> {

    private PlayerCRUDService service;

    private PlayerTeamDataService playerTeamDataService;

    @GetMapping(value = "/team/{playerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieve team by player id.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully retrieved team."),
            @ApiResponse(code = 400, message = "Input validation error."),
            @ApiResponse(code = 404, message = "No player with given id found")
    })
    public ResponseEntity<TeamResponse> getTeamByPlayerId(@PathVariable int playerId) throws EntityNotFoundException {
        return ResponseEntity.ok(playerTeamDataService.getTeamByPlayerId(playerId));
    }

    @GetMapping(value = "/getAll/{teamId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieve team and all of its players by team id.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully retrieved players and team."),
            @ApiResponse(code = 400, message = "Input validation error."),
            @ApiResponse(code = 404, message = "No team with given id found")
    })
    public ResponseEntity<TeamPlayersResponse> getPlayersAndTeamByTeamId(@PathVariable int teamId) throws EntityNotFoundException {
        return ResponseEntity.ok(playerTeamDataService.getPlayersByTeamId(teamId));
    }

    @ApiOperation(value = "Retrieve player and team data by player id.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully retrieved player and team."),
            @ApiResponse(code = 400, message = "Input validation error."),
            @ApiResponse(code = 404, message = "No player with given id found")
    })
    @GetMapping(value = "/team-data/{playerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerTeamDataResponse> getPlayerTeamData(@PathVariable int playerId) throws EntityNotFoundException {
        return ResponseEntity.ok(playerTeamDataService.getPlayerTeamData(playerId));
    }

    @ApiOperation(value = "Retrieve team by player id.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully retrieved team."),
            @ApiResponse(code = 400, message = "Input validation error.")
    })
    @GetMapping(value = "/team/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlayerTeamDataResponse>> getAllPlayersWithTeams(Pageable pageable) {
        return ResponseEntity.ok(playerTeamDataService.getAllPlayersAndTeam(pageable));
    }
}
