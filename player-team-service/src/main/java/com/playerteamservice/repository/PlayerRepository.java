package com.playerteamservice.repository;

import com.model.Status;
import com.playerteamservice.entity.Player;
import com.playerteamservice.entity.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    @Query("SELECT p.team FROM Player p WHERE p.id = :id")
    Optional<Team> getTeamByPlayerId(@Param("id") int id);

    @Query("SELECT p from Player p WHERE p.team.id = :teamId")
    List<Player> findAllByTeamId(int teamId);

    @Modifying
    @Query("UPDATE Player p SET p.team = :team, p.status = :status WHERE p.team.id = :teamId")
    void updateDataOnPlayerWithTeamId(int teamId, Team team, Status status);

    @Query("Select p from Player p inner join Team t on p.team.id=t.id")
    List<Player> findAllPlayersWithTeamData(Pageable pageable);
}
