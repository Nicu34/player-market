package com.playerteamservice.service;

import com.model.*;
import com.playerteamservice.converter.PlayerResponseConverter;
import com.playerteamservice.converter.PlayerTeamDataResponseConverter;
import com.playerteamservice.converter.TeamResponseConverter;
import com.playerteamservice.entity.Player;
import com.playerteamservice.entity.Team;
import com.playerteamservice.exception.EntityNotFoundException;
import com.playerteamservice.repository.PlayerRepository;
import com.playerteamservice.repository.TeamRepository;
import com.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.playerteamservice.util.TestUtils.mockPlayer;
import static com.playerteamservice.util.TestUtils.mockTeam;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class PlayerTeamDataServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private PlayerResponseConverter playerResponseConverter;

    @Mock
    private TeamResponseConverter teamResponseConverter;

    @Mock
    private PlayerTeamDataResponseConverter playerTeamDataResponseConverter;

    @InjectMocks
    private PlayerTeamDataService playerTeamDataService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void getPlayersByTeamId() throws EntityNotFoundException {
        Team team = new Team();
        TeamResponse teamResponse = new TeamResponse();
        List<Player> playerList = List.of(new Player());
        List<PlayerResponse> playerResponseList = List.of(new PlayerResponse());
        when(teamRepository.findById(eq(TestUtils.ID))).thenReturn(Optional.of(team));
        when(teamResponseConverter.convert(eq(team))).thenReturn(teamResponse);
        when(playerRepository.findAllByTeamId(eq(TestUtils.ID))).thenReturn(playerList);
        when(playerResponseConverter.convertFrom(eq(playerList))).thenReturn(playerResponseList);

        TeamPlayersResponse teamPlayersResponse = playerTeamDataService.getPlayersByTeamId(TestUtils.ID);

        assertNotNull(teamPlayersResponse);
        assertEquals(playerResponseList, teamPlayersResponse.getPlayers());
        assertEquals(teamResponse, teamPlayersResponse.getTeam());
    }

    @Test
    void getPlayersByTeamIdThrowsEntityNotFoundException() {
        when(teamRepository.findById(eq(TestUtils.ID))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> playerTeamDataService.getPlayersByTeamId(TestUtils.ID));
    }

    @Test
    void getTeamByPlayerId() throws EntityNotFoundException {
        Team team = new Team();
        TeamResponse expectedResponse = new TeamResponse();
        when(playerRepository.getTeamByPlayerId(TestUtils.ID)).thenReturn(Optional.of(team));
        when(teamResponseConverter.convert(eq(team))).thenReturn(expectedResponse);

        assertEquals(expectedResponse, playerTeamDataService.getTeamByPlayerId(TestUtils.ID));
    }

    @Test
    void getTeamByPlayerIdTeamIdThrowsEntityNotFoundException() {
        when(playerRepository.getTeamByPlayerId(eq(TestUtils.ID))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> playerTeamDataService.getTeamByPlayerId(TestUtils.ID));
    }

    @ParameterizedTest
    @MethodSource("playerTeamDataInput")
    void getPlayerTeamData(Team team, Status status, TeamResponse teamResponse) throws EntityNotFoundException {
        PlayerResponse playerResponse = new PlayerResponse();
        Player player = mockPlayer(team, status);
        when(playerRepository.findById(TestUtils.ID)).thenReturn(Optional.of(player));
        when(teamResponseConverter.convert(eq(team))).thenReturn(teamResponse);
        when(playerResponseConverter.convert(eq(player))).thenReturn(playerResponse);

        PlayerTeamDataResponse playerTeamDataResponse = playerTeamDataService.getPlayerTeamData(TestUtils.ID);

        assertNotNull(playerTeamDataResponse);
        assertEquals(teamResponse, playerTeamDataResponse.getTeam());
        assertEquals(playerResponse, playerTeamDataResponse.getPlayer());
    }

    @Test
    void getPlayerTeamDataThrowsEntityNotFoundException() {
        when(playerRepository.findById(TestUtils.ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> playerTeamDataService.getPlayerTeamData(TestUtils.ID));
    }

    @Test
    void getAllPlayersAndTeam() {
        List<Player> players = List.of(new Player());
        List<PlayerTeamDataResponse> expectedResponse = List.of(new PlayerTeamDataResponse());
        when(playerRepository.findAllPlayersWithTeamData(any())).thenReturn(players);
        when(playerTeamDataResponseConverter.convertFrom(eq(players))).thenReturn(expectedResponse);

        List<PlayerTeamDataResponse> actualResponse = playerTeamDataService.getAllPlayersAndTeam(null);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    private static Stream<Arguments> playerTeamDataInput() {
        return Stream.of(
                Arguments.of(mockTeam(), Status.HIRED, new TeamResponse()),
                Arguments.of(mockTeam(), Status.FREE_AGENT, null)
        );
    }
}