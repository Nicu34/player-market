package com.playerteamservice.service;

import com.model.PlayerResponse;
import com.playerteamservice.converter.PlayerRequestConverter;
import com.playerteamservice.converter.PlayerResponseConverter;
import com.playerteamservice.dto.PlayerRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.playerteamservice.util.TestUtils.mockPlayerRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class PlayerCRUDServiceTest {

    @Mock
    private PlayerRepository repository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private PlayerResponseConverter responseConverter;

    @Mock
    private PlayerRequestConverter playerRequestConverter;

    @InjectMocks
    private PlayerCRUDService playerCRUDService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void save() throws EntityNotFoundException {
        PlayerRequest playerRequest = mockPlayerRequest(TestUtils.ID);
        Team team = new Team();
        Player player = new Player();
        when(teamRepository.findById(eq(TestUtils.ID)))
                .thenReturn(Optional.of(team));
        when(playerRequestConverter.convert(eq(playerRequest), eq(team)))
                .thenReturn(player);
        when(repository.save(eq(player)))
                .thenReturn(null);

        playerCRUDService.save(playerRequest);

        verify(repository).save(eq(player));
    }

    @Test
    void saveThrowsEntityNotFoundException() {
        PlayerRequest playerRequest = mockPlayerRequest(TestUtils.ID);
        when(teamRepository.findById(eq(TestUtils.ID)))
                .thenReturn(Optional.empty());

        Throwable e = assertThrows(EntityNotFoundException.class, () -> playerCRUDService.save(playerRequest));
        assertEquals(String.format("No team with id %s found.", TestUtils.ID), e.getMessage());
    }

    @Test
    void update() throws EntityNotFoundException {
        PlayerRequest playerRequest = mockPlayerRequest(TestUtils.ID);
        Team team = new Team();
        Player player = new Player();
        when(teamRepository.findById(eq(TestUtils.ID)))
                .thenReturn(Optional.of(team));
        when(repository.findById(eq(TestUtils.ID)))
                .thenReturn(Optional.of(player));
        doNothing().when(playerRequestConverter)
                .setPlayerData(eq(player), eq(playerRequest), eq(team));

        playerCRUDService.update(playerRequest, TestUtils.ID);

        verify(playerRequestConverter).setPlayerData(eq(player), eq(playerRequest), eq(team));
    }

    @Test
    void findAll() {
        Pageable pageable = mock(Pageable.class);
        List<Player> playerList = List.of(new Player());
        Page<Player> playerPage = mock(Page.class);
        List<PlayerResponse> expectedResponse = List.of(new PlayerResponse());
        when(repository.findAll(any(Pageable.class))).thenReturn(playerPage);
        when(playerPage.getContent()).thenReturn(playerList);
        when(responseConverter.convertFrom(eq(playerList))).thenReturn(expectedResponse);

        assertEquals(expectedResponse, playerCRUDService.findAll(pageable));
    }

    @Test
    void delete() {
        doNothing().when(repository).deleteById(eq(TestUtils.ID));
        playerCRUDService.delete(TestUtils.ID);
        verify(repository).deleteById(eq(TestUtils.ID));
    }

    @Test
    void getById() throws EntityNotFoundException {
        Player player = new Player();
        PlayerResponse expectedResponse = new PlayerResponse();
        when(repository.findById(eq(TestUtils.ID)))
                .thenReturn(Optional.of(player));
        when(responseConverter.convert(eq(player)))
                .thenReturn(expectedResponse);

        PlayerResponse actualResponse = playerCRUDService.getById(TestUtils.ID);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getByIdThrowsEntityNotFoundException() {
        when(repository.findById(eq(TestUtils.ID)))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> playerCRUDService.getById(TestUtils.ID));
    }

    @ParameterizedTest
    @MethodSource("updateParameters")
    void updateEntityNotFoundException(String message, Team team) {
        PlayerRequest playerRequest = mockPlayerRequest(TestUtils.ID);
        when(teamRepository.findById(eq(TestUtils.ID)))
                .thenReturn(Optional.ofNullable(team));
        when(repository.findById(eq(TestUtils.ID)))
                .thenReturn(Optional.empty());

        Throwable e = assertThrows(EntityNotFoundException.class, () -> playerCRUDService.update(playerRequest, TestUtils.ID));
        assertEquals(String.format(message, TestUtils.ID), e.getMessage());
    }

    private static Stream<Arguments> updateParameters() {
        return Stream.of(
                Arguments.of("No player with id %s found.", new Team()),
                Arguments.of("No team with id %s found.", null)
        );
    }
}