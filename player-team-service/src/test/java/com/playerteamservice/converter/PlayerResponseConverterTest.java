package com.playerteamservice.converter;

import com.model.PlayerResponse;
import com.model.Status;
import com.playerteamservice.entity.Player;
import com.playerteamservice.util.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerResponseConverterTest {

    private PlayerResponseConverter playerResponseConverter = new PlayerResponseConverter();

    @Test
    void convert() {
        Player player = TestUtils.mockPlayer(TestUtils.mockTeam(), Status.HIRED);

        PlayerResponse playerResponse = playerResponseConverter.convert(player);

        verifyPlayerResponse(player, playerResponse);
    }

    @Test
    void convertFromList() {
        List<Player> players = List.of(TestUtils.mockPlayer(TestUtils.mockTeam(), Status.HIRED));

        List<PlayerResponse> playerResponse = playerResponseConverter.convertFrom(players);

        assertNotNull(playerResponse);
        assertFalse(playerResponse.isEmpty());
        verifyPlayerResponse(players.get(0), playerResponse.get(0));
    }

    private void verifyPlayerResponse(Player player, PlayerResponse playerResponse) {
        assertNotNull(playerResponse);
        assertEquals(player.getName(), playerResponse.getName());
        assertEquals(player.getSurname(), playerResponse.getSurname());
        assertEquals(player.getStatus(), playerResponse.getStatus());
        assertEquals(player.getId(), playerResponse.getId());
        assertEquals(player.getHiringDate(), playerResponse.getHiringDate());
        assertEquals(player.getBirthDate(), playerResponse.getBirthDate());
    }
}