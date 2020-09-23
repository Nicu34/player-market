package com.feecalculatorservice.client;

import com.util.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PTSClientFallBackTest {

    private PTSClientFallBack ptsClientFallBack = new PTSClientFallBack();

    @Test
    void retrievePlayer() {
        assertEquals(Optional.empty(), ptsClientFallBack.retrievePlayer(TestUtils.ID));
    }

    @Test
    void retrievePlayersByTeamId() {
        assertEquals(Optional.empty(), ptsClientFallBack.retrievePlayersByTeamId(TestUtils.ID));
    }

    @Test
    void retrievePlayerTeamData() {
        assertEquals(Optional.empty(), ptsClientFallBack.retrievePlayerTeamData(TestUtils.ID));
    }

    @Test
    void retrievePlayersAndTeamData() {
        assertEquals(Optional.empty(), ptsClientFallBack.retrievePlayersAndTeamData(null));
    }

}