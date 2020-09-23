package com.feecalculatorservice.business;

import com.feecalculatorservice.dto.PlayerFeeResponse;
import com.model.CurrencyRatesResponse;
import com.model.PlayerResponse;
import com.model.PlayerTeamDataResponse;
import com.model.TeamResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class FeeHelperTest {

    @Mock
    private FeeCalculator feeCalculator;

    @InjectMocks
    private FeeHelper feeHelper;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @ParameterizedTest
    @MethodSource("playerFeeResponseInputParameters")
    void buildPlayerFeeResponse(String teamCurrency, double inputConversionRate, String actualCurrency, double actualConversionRate) {
        PlayerResponse player = mockPlayerResponse();
        TeamResponse team = mockTeamResponse(teamCurrency);
        CurrencyRatesResponse currencyRates = mockCurrencyRatesResponse(Map.of(actualCurrency, inputConversionRate));
        when(feeCalculator.calculateTransferFee(eq(player.getHiringDate()), eq(player.getBirthDate()), eq(DATE), eq(actualConversionRate)))
                .thenReturn(FEE_AMOUNT);
        when(feeCalculator.calculateTeamCommission(eq(FEE_AMOUNT), eq(actualConversionRate)))
                .thenReturn(FEE_AMOUNT);
        when(feeCalculator.calculateContractFee(eq(FEE_AMOUNT), eq(FEE_AMOUNT), eq(actualConversionRate)))
                .thenReturn(FEE_AMOUNT);

        PlayerFeeResponse playerFeeResponse = feeHelper.buildPlayerFeeResponse(player, team, DATE, currencyRates);

        verifyPlayerFeeResponse(playerFeeResponse, actualCurrency, team, player);
    }

    @Test
    void buildPlayerFeeResponseList() {
        PlayerResponse player = mockPlayerResponse();
        List<PlayerResponse> players = List.of(player);
        TeamResponse team = mockTeamResponse(GBP_CURRENCY);
        CurrencyRatesResponse currencyRates = mockCurrencyRatesResponse(Map.of(GBP_CURRENCY, CONVERSION_RATE));
        when(feeCalculator.calculateTransferFee(eq(player.getHiringDate()), eq(player.getBirthDate()), eq(DATE), eq(CONVERSION_RATE)))
                .thenReturn(FEE_AMOUNT);
        when(feeCalculator.calculateTeamCommission(eq(FEE_AMOUNT), eq(CONVERSION_RATE)))
                .thenReturn(FEE_AMOUNT);
        when(feeCalculator.calculateContractFee(eq(FEE_AMOUNT), eq(FEE_AMOUNT), eq(CONVERSION_RATE)))
                .thenReturn(FEE_AMOUNT);

        List<PlayerFeeResponse> playerFeeResponseList = feeHelper.buildPlayerFeeResponseList(players, team, DATE, currencyRates);

        assertNotNull(playerFeeResponseList);
        assertFalse(playerFeeResponseList.isEmpty());
        PlayerFeeResponse playerFeeResponse = playerFeeResponseList.get(0);
        verifyPlayerFeeResponse(playerFeeResponse, GBP_CURRENCY, team, player);
    }

    @Test
    void testBuildPlayerFeeResponseList() {
        PlayerResponse player = mockPlayerResponse();
        TeamResponse team = mockTeamResponse(GBP_CURRENCY);
        PlayerTeamDataResponse playerTeamDataResponse = PlayerTeamDataResponse.builder()
                .player(player)
                .team(team)
                .build();
        List<PlayerTeamDataResponse> playerTeamDataResponses = List.of(playerTeamDataResponse);
        CurrencyRatesResponse currencyRates = mockCurrencyRatesResponse(Map.of(GBP_CURRENCY, CONVERSION_RATE));
        when(feeCalculator.calculateTransferFee(eq(player.getHiringDate()), eq(player.getBirthDate()), eq(DATE), eq(CONVERSION_RATE)))
                .thenReturn(FEE_AMOUNT);
        when(feeCalculator.calculateTeamCommission(eq(FEE_AMOUNT), eq(CONVERSION_RATE)))
                .thenReturn(FEE_AMOUNT);
        when(feeCalculator.calculateContractFee(eq(FEE_AMOUNT), eq(FEE_AMOUNT), eq(CONVERSION_RATE)))
                .thenReturn(FEE_AMOUNT);

        List<PlayerFeeResponse> playerFeeResponseList = feeHelper.buildPlayerFeeResponseList(playerTeamDataResponses, DATE, currencyRates);

        assertNotNull(playerFeeResponseList);
        assertFalse(playerFeeResponseList.isEmpty());
        PlayerFeeResponse playerFeeResponse = playerFeeResponseList.get(0);
        verifyPlayerFeeResponse(playerFeeResponse, GBP_CURRENCY, team, player);
    }

    private void verifyPlayerFeeResponse(PlayerFeeResponse playerFeeResponse, String currency, TeamResponse team,
                                         PlayerResponse player) {
        assertNotNull(playerFeeResponse);
        assertEquals(player.getName(), playerFeeResponse.getName());
        assertEquals(player.getSurname(), playerFeeResponse.getSurname());
        assertEquals(currency, playerFeeResponse.getCurrency());
        assertEquals(team.getName(), playerFeeResponse.getTeam());
        assertEquals(FEE_AMOUNT, playerFeeResponse.getContractFee());
        assertEquals(FEE_AMOUNT, playerFeeResponse.getTeamCommission());
        assertEquals(FEE_AMOUNT, playerFeeResponse.getTransferFee());
        assertEquals(DATE, playerFeeResponse.getDate());
    }

    private static Stream<Arguments> playerFeeResponseInputParameters() {
        return Stream.of(
                Arguments.of(GBP_CURRENCY, CONVERSION_RATE, GBP_CURRENCY, CONVERSION_RATE),
                Arguments.of(GBP_CURRENCY, CONVERSION_RATE, EUR_CURRENCY, DEFAULT_CONVERSION_RATE)
        );
    }

}