package com.playerteamservice.controller;

import com.model.PlayerTeamDataResponse;
import com.model.TeamPlayersResponse;
import com.model.TeamResponse;
import com.playerteamservice.exception.EntityNotFoundException;
import com.playerteamservice.service.PlayerTeamDataService;
import com.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class PlayerControllerTest {

    @Mock
    private PlayerTeamDataService playerTeamDataService;

    @InjectMocks
    private PlayerController playerController;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void getTeamByPlayerId() throws EntityNotFoundException {
        TeamResponse teamResponse = mock(TeamResponse.class);
        when(playerTeamDataService.getTeamByPlayerId(eq(TestUtils.ID)))
                .thenReturn(teamResponse);

        ResponseEntity<TeamResponse> actualResponse = playerController.getTeamByPlayerId(TestUtils.ID);

        assertNotNull(actualResponse);
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(teamResponse, actualResponse.getBody());
    }

    @Test
    void getPlayersAndTeamByTeamId() throws EntityNotFoundException {
        TeamPlayersResponse teamPlayersResponse = mock(TeamPlayersResponse.class);
        when(playerTeamDataService.getPlayersByTeamId(eq(TestUtils.ID)))
                .thenReturn(teamPlayersResponse);

        ResponseEntity<TeamPlayersResponse> actualResponse = playerController.getPlayersAndTeamByTeamId(TestUtils.ID);

        assertNotNull(actualResponse);
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(teamPlayersResponse, actualResponse.getBody());
    }

    @Test
    void getPlayerTeamData() throws EntityNotFoundException {
        PlayerTeamDataResponse playerTeamDataResponse = mock(PlayerTeamDataResponse.class);
        when(playerTeamDataService.getPlayerTeamData(eq(TestUtils.ID)))
                .thenReturn(playerTeamDataResponse);

        ResponseEntity<PlayerTeamDataResponse> actualResponse = playerController.getPlayerTeamData(TestUtils.ID);

        assertNotNull(actualResponse);
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(playerTeamDataResponse, actualResponse.getBody());
    }

    @Test
    void getAllPlayersWithTeams() {
        List<PlayerTeamDataResponse> playerTeamDataResponse = List.of(mock(PlayerTeamDataResponse.class));
        when(playerTeamDataService.getAllPlayersAndTeam(any()))
                .thenReturn(playerTeamDataResponse);

        ResponseEntity<List<PlayerTeamDataResponse>> actualResponse = playerController.getAllPlayersWithTeams(any());

        assertNotNull(actualResponse);
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(playerTeamDataResponse, actualResponse.getBody());
    }

}