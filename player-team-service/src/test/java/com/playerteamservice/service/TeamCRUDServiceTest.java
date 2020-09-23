package com.playerteamservice.service;

import com.model.Status;
import com.playerteamservice.converter.TeamRequestConverter;
import com.playerteamservice.converter.TeamResponseConverter;
import com.playerteamservice.dto.TeamRequest;
import com.playerteamservice.entity.Team;
import com.playerteamservice.exception.EntityNotFoundException;
import com.playerteamservice.repository.PlayerRepository;
import com.playerteamservice.repository.TeamRepository;
import com.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class TeamCRUDServiceTest {

    @Mock
    private TeamRepository repository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamRequestConverter teamRequestConverter;

    @InjectMocks
    private TeamCRUDService teamCRUDService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void save() {
        TeamRequest teamRequest = new TeamRequest();
        Team team = new Team();
        when(teamRequestConverter.convert(eq(teamRequest)))
                .thenReturn(team);
        when(repository.save(eq(team))).thenReturn(team);

        teamCRUDService.save(teamRequest);

        verify(repository).save(eq(team));
    }

    @Test
    void update() throws EntityNotFoundException {
        TeamRequest teamRequest = mock(TeamRequest.class);
        Team team = mock(Team.class);
        when(repository.findById(eq(TestUtils.ID)))
                .thenReturn(Optional.of(team));

        teamCRUDService.update(teamRequest, TestUtils.ID);
        verify(team).setName(eq(teamRequest.getName()));
        verify(team).setCurrency(eq(teamRequest.getCurrency()));
    }

    @Test
    void updateThrowsException() {
        when(repository.findById(eq(TestUtils.ID)))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teamCRUDService.update(new TeamRequest(), TestUtils.ID));
    }

    @Test
    void delete() {
        doNothing().when(playerRepository)
                .updateDataOnPlayerWithTeamId(eq(TestUtils.ID), eq(null), eq(Status.FREE_AGENT));
        doNothing().when(repository)
                .deleteById(eq(TestUtils.ID));

        teamCRUDService.delete(TestUtils.ID);

        verify(playerRepository).updateDataOnPlayerWithTeamId(eq(TestUtils.ID), eq(null), eq(Status.FREE_AGENT));
        verify(repository).deleteById(eq(TestUtils.ID));

    }
}