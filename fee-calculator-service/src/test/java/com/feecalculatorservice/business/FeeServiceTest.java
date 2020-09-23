package com.feecalculatorservice.business;

import com.feecalculatorservice.client.CurrencyClient;
import com.feecalculatorservice.client.PTSClient;
import com.model.CurrencyRatesResponse;
import com.feecalculatorservice.dto.PlayerFeeResponse;
import com.feecalculatorservice.exception.PTSClientException;
import com.model.PlayerTeamDataResponse;
import com.model.TeamPlayersResponse;
import com.util.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class FeeServiceTest {

    @Mock
    private PTSClient ptsClient;

    @Mock
    private CurrencyClient currencyClient;

    @Mock
    private FeeHelper feeHelper;

    @InjectMocks
    private FeeService feeService;

    @BeforeEach
    void setUp() {
        initMocks(this);
        when(currencyClient.retrieveCurrencyRates(eq(TestUtils.DATE_FORMATTER.format(TestUtils.DATE))))
                .thenReturn(new CurrencyRatesResponse());
    }

    @Test
    void calculatePlayerFees() throws PTSClientException {
        PlayerFeeResponse expectedResponse = new PlayerFeeResponse();
        when(ptsClient.retrievePlayerTeamData(eq(TestUtils.ID)))
                .thenReturn(Optional.of(new PlayerTeamDataResponse()));
        when(feeHelper.buildPlayerFeeResponse(any(), any(), eq(TestUtils.DATE), any(CurrencyRatesResponse.class)))
                .thenReturn(expectedResponse);

        PlayerFeeResponse actualResponse = feeService.calculatePlayerFees(TestUtils.ID, TestUtils.DATE);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void calculatePlayerFeesThrowsPTSClientException() {
        when(ptsClient.retrievePlayerTeamData(eq(TestUtils.ID)))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(PTSClientException.class, () -> feeService.calculatePlayerFees(TestUtils.ID, TestUtils.DATE));
    }

    @Test
    void calculateTeamPlayerFees() throws PTSClientException {
        List<PlayerFeeResponse> expectedResponse = List.of(new PlayerFeeResponse());
        when(ptsClient.retrievePlayersByTeamId(eq(TestUtils.ID)))
                .thenReturn(Optional.of(new TeamPlayersResponse()));
        when(feeHelper.buildPlayerFeeResponseList(any(), any(), eq(TestUtils.DATE), any(CurrencyRatesResponse.class)))
                .thenReturn(expectedResponse);

        List<PlayerFeeResponse> actualResponse = feeService.calculateTeamPlayerFees(TestUtils.ID, TestUtils.DATE);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void calculateTeamPlayerFeesThrowsPTSClientException() {
        when(ptsClient.retrievePlayersByTeamId(eq(TestUtils.ID)))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(PTSClientException.class, () -> feeService.calculatePlayerFees(TestUtils.ID, TestUtils.DATE));
    }

    @Test
    void calculateAllPlayerFees() throws PTSClientException {
        List<PlayerFeeResponse> expectedResponse = List.of(new PlayerFeeResponse());
        when(ptsClient.retrievePlayersAndTeamData(any()))
                .thenReturn(Optional.of(List.of(new PlayerTeamDataResponse())));
        when(feeHelper.buildPlayerFeeResponseList(any(), eq(TestUtils.DATE), any(CurrencyRatesResponse.class)))
                .thenReturn(expectedResponse);

        List<PlayerFeeResponse> actualResponse = feeService.calculateAllPlayerFees(TestUtils.DATE, null);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void calculateAllPlayerFeesThrowsPTSClientException() {
        when(ptsClient.retrievePlayersAndTeamData(any(Pageable.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(PTSClientException.class, () -> feeService.calculatePlayerFees(TestUtils.ID, TestUtils.DATE));
    }
}