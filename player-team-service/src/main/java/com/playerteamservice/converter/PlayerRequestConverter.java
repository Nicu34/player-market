package com.playerteamservice.converter;

import com.model.Status;
import com.playerteamservice.dto.PlayerRequest;
import com.playerteamservice.entity.Player;
import com.playerteamservice.entity.Team;
import org.springframework.stereotype.Component;

@Component
public class PlayerRequestConverter {

    public Player convert(PlayerRequest request, Team team) {
        return Player.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .status(getPlayerStatus(request))
                .team(team)
                .hiringDate(request.getHiringDate())
                .birthDate(request.getBirthDate())
                .build();
    }

    public void setPlayerData(Player player, PlayerRequest request, Team team) {
        player.setTeam(team);
        player.setBirthDate(request.getBirthDate());
        player.setHiringDate(request.getHiringDate());
        player.setStatus(getPlayerStatus(request));
        player.setSurname(request.getSurname());
        player.setName(request.getName());
    }

    private Status getPlayerStatus(PlayerRequest playerRequest) {
        return playerRequest.getTeamId() == null ? Status.FREE_AGENT : Status.HIRED;
    }

}
