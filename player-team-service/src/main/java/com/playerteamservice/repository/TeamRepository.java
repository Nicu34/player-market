package com.playerteamservice.repository;

import com.playerteamservice.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    @Modifying
    @Query("UPDATE Team t SET t.name = :name, t.currency = :currency WHERE t.id = :id")
    void updateById(int id, String name, String currency);
}
