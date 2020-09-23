package com.playerteamservice.service;

import com.model.Status;
import com.playerteamservice.exception.EntityNotFoundException;
import com.playerteamservice.converter.TeamRequestConverter;
import com.playerteamservice.converter.TeamResponseConverter;
import com.playerteamservice.dto.TeamRequest;
import com.model.TeamResponse;
import com.playerteamservice.entity.Team;
import com.playerteamservice.repository.PlayerRepository;
import com.playerteamservice.repository.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Getter
public class TeamCRUDService extends GenericCRUDService<Team, TeamResponse, TeamRequest> {

    private TeamRepository repository;

    private PlayerRepository playerRepository;

    private TeamResponseConverter responseConverter;

    private TeamRequestConverter teamRequestConverter;

    @Override
    public void save(TeamRequest request) {
        Team team = teamRequestConverter.convert(request);

        repository.save(team);
    }

    @Override
    @Transactional
    public void update(TeamRequest request, Integer teamId) throws EntityNotFoundException {
        Team team = repository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No team with id %s found.", teamId)));

        team.setName(request.getName());
        team.setCurrency(request.getCurrency());
    }

    @Override
    @Transactional
    public void delete(Integer teamId) {
        playerRepository.updateDataOnPlayerWithTeamId(teamId, null, Status.FREE_AGENT);
        repository.deleteById(teamId);
    }
}
