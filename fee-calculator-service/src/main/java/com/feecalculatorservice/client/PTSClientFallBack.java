package com.feecalculatorservice.client;

import com.model.PlayerResponse;
import com.model.PlayerTeamDataResponse;
import com.model.TeamPlayersResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class PTSClientFallBack implements PTSClient {

    @Override
    public Optional<PlayerResponse> retrievePlayer(int id) {
        log.error("Failed to retrieve player from Player Transfer Service.");
        return Optional.empty();
    }

    @Override
    public Optional<TeamPlayersResponse> retrievePlayersByTeamId(int teamId) {
        log.error("Failed to retrieve players from Player Transfer Service.");
        return Optional.empty();
    }

    @Override
    public Optional<PlayerTeamDataResponse> retrievePlayerTeamData(int playerId) {
        log.error("Failed to retrieve player and team data from Player Transfer Service.");
        return Optional.empty();
    }

    @Override
    public Optional<List<PlayerTeamDataResponse>> retrievePlayersAndTeamData(Pageable pageable) {
        log.error("Failed to retrieve players and their team data from Player Transfer Service.");
        return Optional.empty();
    }

}
