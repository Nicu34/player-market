package com.feecalculatorservice.client;

import com.model.PlayerResponse;
import com.model.PlayerTeamDataResponse;
import com.model.TeamPlayersResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "player-team-service",
        url = "http://127.0.0.1:8080/api/",
        fallback = PTSClientFallBack.class)
public interface PTSClient {

    @GetMapping("/player/get/{id}")
    Optional<PlayerResponse> retrievePlayer(@PathVariable int id);

    @GetMapping("/player/getAll/{teamId}")
    Optional<TeamPlayersResponse> retrievePlayersByTeamId(@PathVariable int teamId);

    @GetMapping("/player/team-data/{playerId}")
    Optional<PlayerTeamDataResponse> retrievePlayerTeamData(@PathVariable int playerId);

    @GetMapping("/player/team/getAll")
    Optional<List<PlayerTeamDataResponse>> retrievePlayersAndTeamData(Pageable pageable);

}
