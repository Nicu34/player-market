package com.playerteamservice.service;

import com.playerteamservice.converter.PlayerRequestConverter;
import com.playerteamservice.converter.PlayerResponseConverter;
import com.playerteamservice.dto.PlayerRequest;
import com.model.PlayerResponse;
import com.playerteamservice.entity.Player;
import com.playerteamservice.entity.Team;
import com.playerteamservice.exception.EntityNotFoundException;
import com.playerteamservice.repository.PlayerRepository;
import com.playerteamservice.repository.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Getter
public class PlayerCRUDService extends GenericCRUDService<Player, PlayerResponse, PlayerRequest> {

    private PlayerRepository repository;

    private TeamRepository teamRepository;

    private PlayerResponseConverter responseConverter;

    private PlayerRequestConverter playerRequestConverter;

    /**
     * Creates a new player and persists it.
     * If a teamId is provided via PlayerRequest object, then the player will be created within a team with HIRED status.
     * If a teamId is not provided,
     *
     * @param request - object containing player data
     * @throws EntityNotFoundException if team id is provided, but there is no Team with such id provided into database
     */
    @Override
    public void save(PlayerRequest request) throws EntityNotFoundException {
        Team team = getTeamById(request.getTeamId());

        Player player = playerRequestConverter.convert(request, team);

        repository.save(player);
    }

    @Override
    @Transactional
    public void update(PlayerRequest request, Integer playerId) throws EntityNotFoundException {
        Team team = getTeamById(request.getTeamId());
        Player player = repository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No player with id %s found.", playerId)));

        playerRequestConverter.setPlayerData(player, request, team);
    }

    private Team getTeamById(Integer teamId) throws EntityNotFoundException {
        Team team = null;

        // try to get the team from database if id is provided
        if (teamId != null) {
            team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("No team with id %s found.", teamId)));
        }

        return team;
    }
}
