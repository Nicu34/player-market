package com.playerteamservice.controller;

import com.playerteamservice.dto.TeamRequest;
import com.model.TeamResponse;
import com.playerteamservice.service.TeamCRUDService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/team")
@AllArgsConstructor
@Getter
@Api(tags = "Team based operations.")
public class TeamController extends GenericCRUDController<TeamResponse, TeamRequest>{

    private TeamCRUDService service;

}
