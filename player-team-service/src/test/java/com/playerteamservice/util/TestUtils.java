package com.playerteamservice.util;

import com.model.Status;
import com.model.TeamResponse;
import com.playerteamservice.dto.PlayerRequest;
import com.playerteamservice.dto.TeamRequest;
import com.playerteamservice.entity.Player;
import com.playerteamservice.entity.Team;

import static com.util.TestUtils.*;

public class TestUtils {

    public static PlayerRequest mockPlayerRequest(Integer teamId) {
        return PlayerRequest.builder()
                .birthDate(DATE)
                .hiringDate(DATE)
                .surname("surname")
                .name("name")
                .teamId(teamId)
                .build();
    }

    public static Team mockTeam() {
        return Team.builder()
                .name("name")
                .currency(EUR_CURRENCY)
                .build();
    }

    public static Player mockPlayer(Team team, Status status) {
        return Player.builder()
                .team(team)
                .status(status)
                .surname("surname")
                .name("name")
                .birthDate(DATE)
                .hiringDate(DATE)
                .id(ID)
                .build();
    }

    public static TeamRequest mockTeamRequest() {
        return TeamRequest.builder()
                .currency(EUR_CURRENCY)
                .name("name")
                .build();
    }

    public static TeamResponse mockTeamResponse() {
        return TeamResponse.builder()
                .name("name")
                .currency(EUR_CURRENCY)
                .id(ID)
                .build();
    }
}
