package com.feecalculatorservice.controller;

import com.feecalculatorservice.business.FeeService;
import com.feecalculatorservice.dto.PlayerFeeResponse;
import com.feecalculatorservice.exception.PTSClientException;
import com.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class FeeDataControllerTest {

    @Mock
    private FeeService feeService;

    @InjectMocks
    private FeeDataController feeDataController;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void calculatePlayerFees() throws PTSClientException {
        PlayerFeeResponse playerFeeResponse = mock(PlayerFeeResponse.class);
        when(feeService.calculatePlayerFees(eq(TestUtils.ID), eq(TestUtils.DATE))).
                thenReturn(playerFeeResponse);

        assertEquals(playerFeeResponse, feeDataController.calculatePlayerFees(TestUtils.ID, TestUtils.DATE));
    }

    @Test
    void calculateTeamPlayerFees() throws PTSClientException {
        List<PlayerFeeResponse> playerFeeResponseList = List.of(mock(PlayerFeeResponse.class));
        when(feeService.calculateTeamPlayerFees(eq(TestUtils.ID), eq(TestUtils.DATE)))
                .thenReturn(playerFeeResponseList);

        assertEquals(playerFeeResponseList, feeDataController.calculateTeamPlayerFees(TestUtils.ID, TestUtils.DATE));
    }

    @Test
    void calculateAllPlayerFees() throws PTSClientException {
        List<PlayerFeeResponse> playerFeeResponseList = List.of(mock(PlayerFeeResponse.class));
        when(feeService.calculateAllPlayerFees(eq(TestUtils.DATE), any()))
                .thenReturn(playerFeeResponseList);

        assertEquals(playerFeeResponseList, feeDataController.calculateAllPlayerFees(TestUtils.DATE, null));
    }
}