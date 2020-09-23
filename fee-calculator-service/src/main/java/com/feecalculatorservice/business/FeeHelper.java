package com.feecalculatorservice.business;

import com.feecalculatorservice.dto.*;
import com.model.CurrencyRatesResponse;
import com.model.PlayerResponse;
import com.model.PlayerTeamDataResponse;
import com.model.TeamResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class FeeHelper {
    private static final String DEFAULT_CURRENCY = "EUR";
    private static final double DEFAULT_CONVERSION_RATE = 1;

    private FeeCalculator feeCalculator;

    public PlayerFeeResponse buildPlayerFeeResponse(PlayerResponse player, TeamResponse team, LocalDate currentDate,
                                                    CurrencyRatesResponse currencyRates) {
        String currency = team.getCurrency();
        double conversionRate = getConversionRate(currencyRates, currency);
        double transferFee = feeCalculator.calculateTransferFee(player.getHiringDate(), player.getBirthDate(), currentDate, conversionRate);
        double teamCommission = feeCalculator.calculateTeamCommission(transferFee, conversionRate);
        double contractFee = feeCalculator.calculateContractFee(transferFee, teamCommission, conversionRate);

        return PlayerFeeResponse.builder()
                .name(player.getName())
                .surname(player.getSurname())
                .currency(getCurrency(currencyRates, team.getCurrency()))
                .team(team.getName())
                .contractFee(contractFee)
                .teamCommission(teamCommission)
                .transferFee(transferFee)
                .date(currentDate)
                .build();
    }

    public List<PlayerFeeResponse> buildPlayerFeeResponseList(List<PlayerResponse> playerResponse, TeamResponse team,
                                                               LocalDate currentDate, CurrencyRatesResponse currencyRates) {
        return playerResponse.stream()
                .map(p -> buildPlayerFeeResponse(p, team, currentDate, currencyRates))
                .collect(Collectors.toList());
    }

    public List<PlayerFeeResponse> buildPlayerFeeResponseList(List<PlayerTeamDataResponse> playerTeamDataResponseList,
                                                              LocalDate currentDate, CurrencyRatesResponse currencyRates) {
        return playerTeamDataResponseList.stream()
                .map(p -> buildPlayerFeeResponse(p.getPlayer(), p.getTeam(), currentDate, currencyRates))
                .collect(Collectors.toList());
    }

    private double getConversionRate(CurrencyRatesResponse currencyRates, String currency) {
        if (CollectionUtils.isEmpty(currencyRates.getRates()) || !currencyRates.getRates().containsKey(currency)) {
            return DEFAULT_CONVERSION_RATE;
        }
        return currencyRates.getRates().get(currency);
    }

    private String getCurrency(CurrencyRatesResponse currencyRates, String currency) {
        if (CollectionUtils.isEmpty(currencyRates.getRates()) || !currencyRates.getRates().containsKey(currency)) {
            return DEFAULT_CURRENCY;
        }
        return currency;
    }
}
