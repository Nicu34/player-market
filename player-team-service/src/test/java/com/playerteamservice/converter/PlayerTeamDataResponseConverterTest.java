package com.playerteamservice.converter;

import com.model.PlayerResponse;
import com.model.PlayerTeamDataResponse;
import com.model.Status;
import com.model.TeamResponse;
import com.playerteamservice.entity.Player;
import com.playerteamservice.entity.Team;
import com.playerteamservice.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.playerteamservice.util.TestUtils.mockTeam;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class PlayerTeamDataResponseConverterTest {

    @Mock
    private TeamResponseConverter teamResponseConverter;

    @Mock
    private PlayerResponseConverter playerResponseConverter;

    @InjectMocks
    private PlayerTeamDataResponseConverter playerTeamDataResponseConverter;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void convert() {
        Team team = mockTeam();
        Player player = TestUtils.mockPlayer(team, Status.HIRED);
        PlayerResponse playerResponse = mock(PlayerResponse.class);
        TeamResponse teamResponse = mock(TeamResponse.class);
        when(playerResponseConverter.convert(eq(player))).thenReturn(playerResponse);
        when(teamResponseConverter.convert(eq(team))).thenReturn(teamResponse);

        PlayerTeamDataResponse playerTeamDataResponse = playerTeamDataResponseConverter.convert(player);

        assertNotNull(playerTeamDataResponse);
        assertEquals(playerResponse, playerTeamDataResponse.getPlayer());
        assertEquals(playerResponse, playerTeamDataResponse.getPlayer());
        assertEquals(teamResponse, playerTeamDataResponse.getTeam());
    }
}