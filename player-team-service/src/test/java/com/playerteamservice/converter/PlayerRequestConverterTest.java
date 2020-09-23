package com.playerteamservice.converter;

import com.model.Status;
import com.playerteamservice.dto.PlayerRequest;
import com.playerteamservice.entity.Player;
import com.playerteamservice.entity.Team;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.playerteamservice.util.TestUtils.mockPlayerRequest;
import static com.playerteamservice.util.TestUtils.mockTeam;
import static com.util.TestUtils.ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PlayerRequestConverterTest {

    private PlayerRequestConverter playerRequestConverter = new PlayerRequestConverter();

    @ParameterizedTest
    @MethodSource("playerRequestConverterParams")
    void convert(Status status, Integer teamId, Team team) {
        PlayerRequest playerRequest = mockPlayerRequest(teamId);

        Player player = playerRequestConverter.convert(playerRequest, team);

        verifyPlayer(player, playerRequest, team, status);
    }

    @ParameterizedTest
    @MethodSource("playerRequestConverterParams")
    void setPlayerData(Status status, Integer teamId, Team team) {
        PlayerRequest playerRequest = mockPlayerRequest(teamId);
        Player player = new Player();

        playerRequestConverter.setPlayerData(player, playerRequest, team);

        verifyPlayer(player, playerRequest, team, status);
    }

    private static Stream<Arguments> playerRequestConverterParams() {
        return Stream.of(
                Arguments.of(Status.FREE_AGENT, null, null),
                Arguments.of(Status.HIRED, ID, mockTeam())
        );
    }

    private void verifyPlayer(Player player, PlayerRequest playerRequest, Team team, Status status) {
        assertNotNull(player);
        assertEquals(playerRequest.getName(), player.getName());
        assertEquals(playerRequest.getBirthDate(), player.getBirthDate());
        assertEquals(playerRequest.getSurname(), player.getSurname());
        assertEquals(status, player.getStatus());
        assertEquals(team, player.getTeam());
        assertEquals(playerRequest.getHiringDate(), player.getHiringDate());
        assertEquals(playerRequest.getBirthDate(), player.getBirthDate());
    }
}