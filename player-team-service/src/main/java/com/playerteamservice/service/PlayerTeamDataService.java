package com.playerteamservice.service;


import com.model.*;
import com.playerteamservice.converter.PlayerResponseConverter;
import com.playerteamservice.converter.PlayerTeamDataResponseConverter;
import com.playerteamservice.converter.TeamResponseConverter;
import com.playerteamservice.entity.Player;
import com.playerteamservice.entity.Team;
import com.playerteamservice.exception.EntityNotFoundException;
import com.playerteamservice.repository.PlayerRepository;
import com.playerteamservice.repository.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PlayerTeamDataService {

    private PlayerRepository playerRepository;

    private TeamRepository teamRepository;

    private PlayerResponseConverter playerResponseConverter;

    private TeamResponseConverter teamResponseConverter;

    private PlayerTeamDataResponseConverter playerTeamDataResponseConverter;

    public TeamPlayersResponse getPlayersByTeamId(int id) throws EntityNotFoundException {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No team with id %s found.", id)));

        TeamResponse teamResponse = teamResponseConverter.convert(team);
        List<PlayerResponse> playerResponseList = playerResponseConverter.convertFrom(playerRepository.findAllByTeamId(id));

        return TeamPlayersResponse.builder()
                .players(playerResponseList)
                .team(teamResponse)
                .build();
    }

    public TeamResponse getTeamByPlayerId(int id) throws EntityNotFoundException {
        Team team = playerRepository.getTeamByPlayerId(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Player with id %s is not assigned to a team.", id)));

        return teamResponseConverter.convert(team);
    }

    public PlayerTeamDataResponse getPlayerTeamData(int playerId) throws EntityNotFoundException {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Player with id %s is not assigned to a team.", playerId)));

        TeamResponse team = null;
        if (Status.HIRED == player.getStatus()) {
            team = teamResponseConverter.convert(player.getTeam());
        }

        return PlayerTeamDataResponse.builder()
                .player(playerResponseConverter.convert(player))
                .team(team)
                .build();

    }

    public List<PlayerTeamDataResponse> getAllPlayersAndTeam(Pageable pageable) {
        List<Player> players = playerRepository.findAllPlayersWithTeamData(pageable);

        return playerTeamDataResponseConverter.convertFrom(players);
    }
}
