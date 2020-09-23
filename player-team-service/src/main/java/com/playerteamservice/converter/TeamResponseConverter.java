package com.playerteamservice.converter;

import com.model.TeamResponse;
import com.playerteamservice.entity.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamResponseConverter extends GenericResponseConverter<Team, TeamResponse> {

    @Override
    public TeamResponse convert(Team source) {
        return TeamResponse.builder()
                .id(source.getId())
                .name(source.getName())
                .currency(source.getCurrency())
                .build();
    }
}
