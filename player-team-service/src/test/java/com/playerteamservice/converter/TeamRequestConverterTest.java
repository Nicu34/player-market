package com.playerteamservice.converter;

import com.playerteamservice.dto.TeamRequest;
import com.playerteamservice.entity.Team;
import org.junit.jupiter.api.Test;

import static com.playerteamservice.util.TestUtils.mockTeamRequest;
import static org.junit.jupiter.api.Assertions.*;

class TeamRequestConverterTest {

    @Test
    void convert() {
        TeamRequestConverter converter = new TeamRequestConverter();
        TeamRequest teamRequest = mockTeamRequest();

        Team team = converter.convert(teamRequest);

        assertNotNull(team);
        assertEquals(teamRequest.getCurrency(), team.getCurrency());
        assertEquals(teamRequest.getName(), teamRequest.getName());
    }
}