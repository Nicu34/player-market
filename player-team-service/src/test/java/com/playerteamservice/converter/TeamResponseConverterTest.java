package com.playerteamservice.converter;

import com.model.TeamResponse;
import com.playerteamservice.entity.Team;
import com.playerteamservice.util.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamResponseConverterTest {

    @Test
    void convert() {
        TeamResponseConverter converter = new TeamResponseConverter();
        Team team = TestUtils.mockTeam();

        TeamResponse teamResponse = converter.convert(team);

        assertNotNull(teamResponse);
        assertEquals(team.getId(), teamResponse.getId());
        assertEquals(team.getName(), teamResponse.getName());
        assertEquals(team.getCurrency(), teamResponse.getCurrency());
    }
}