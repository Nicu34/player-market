package com.playerteamservice.controller;

import com.model.TeamResponse;
import com.playerteamservice.dto.TeamRequest;
import com.playerteamservice.exception.EntityNotFoundException;
import com.playerteamservice.service.TeamCRUDService;
import com.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class TeamControllerTest {

    @Mock
    private TeamCRUDService teamCRUDService;

    @InjectMocks
    private TeamController teamController;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void findAllPlayers() {
        List<TeamResponse> expectedResponse = List.of(new TeamResponse());
        when(teamCRUDService.findAll(any()))
                .thenReturn(expectedResponse);

        ResponseEntity<List<TeamResponse>> actualResponse = teamController.findAllPlayers(null);

        assertNotNull(actualResponse);
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    void save() throws EntityNotFoundException {
        TeamRequest teamRequest = new TeamRequest();
        doNothing().when(teamCRUDService).save(eq(teamRequest));
        teamController.save(teamRequest);
        verify(teamCRUDService).save(eq(teamRequest));
    }

    @Test
    void update() throws EntityNotFoundException {
        TeamRequest teamRequest = new TeamRequest();
        doNothing().when(teamCRUDService).update(eq(teamRequest), eq(TestUtils.ID));
        teamController.update(teamRequest, TestUtils.ID);
        verify(teamCRUDService).update(eq(teamRequest), eq(TestUtils.ID));
    }

    @Test
    void delete() {
        doNothing().when(teamCRUDService).delete(eq(TestUtils.ID));
        teamController.delete(TestUtils.ID);
        verify(teamCRUDService).delete(eq(TestUtils.ID));
    }

}