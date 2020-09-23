package com.playerteamservice.converter;

import com.playerteamservice.dto.TeamRequest;
import com.playerteamservice.entity.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamRequestConverter {

    public Team convert(TeamRequest source) {
        return Team.builder()
                .name(source.getName())
                .currency(source.getCurrency())
                .build();
    }
}
