package com.playerteamservice.converter;

import com.model.PlayerTeamDataResponse;
import com.playerteamservice.entity.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PlayerTeamDataResponseConverter extends GenericResponseConverter<Player, PlayerTeamDataResponse> {

    private TeamResponseConverter teamResponseConverter;
    private PlayerResponseConverter playerResponseConverter;

    public PlayerTeamDataResponse convert(Player player) {
        return PlayerTeamDataResponse.builder()
                .player(playerResponseConverter.convert(player))
                .team(teamResponseConverter.convert(player.getTeam()))
                .build();
    }
}
