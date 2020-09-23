package com.feecalculatorservice.business;

import com.feecalculatorservice.client.CurrencyClient;
import com.feecalculatorservice.client.PTSClient;
import com.model.CurrencyRatesResponse;
import com.feecalculatorservice.dto.PlayerFeeResponse;
import com.feecalculatorservice.exception.PTSClientException;
import com.model.PlayerTeamDataResponse;
import com.model.TeamPlayersResponse;
import com.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class FeeService {

    private PTSClient ptsClient;
    private CurrencyClient currencyClient;
    private FeeHelper feeHelper;

    public PlayerFeeResponse calculatePlayerFees(int playerId, LocalDate date) throws PTSClientException {
        PlayerTeamDataResponse playerTeamDataResponse = ptsClient.retrievePlayerTeamData(playerId)
                .orElseThrow(() -> new PTSClientException("No data retrieved from Player Team service."));
        CurrencyRatesResponse currencyRates = currencyClient.retrieveCurrencyRates(Constants.DATE_FORMATTER.format(date));

        return feeHelper.buildPlayerFeeResponse(playerTeamDataResponse.getPlayer(),
                playerTeamDataResponse.getTeam(), date, currencyRates);
    }

    public List<PlayerFeeResponse> calculateTeamPlayerFees(int teamId, LocalDate date) throws PTSClientException {
        TeamPlayersResponse teamPlayersResponse = ptsClient.retrievePlayersByTeamId(teamId)
                .orElseThrow(() -> new PTSClientException("No team players data retrieved from Player Team service."));
        CurrencyRatesResponse currencyRates = currencyClient.retrieveCurrencyRates(Constants.DATE_FORMATTER.format(date));

        return feeHelper.buildPlayerFeeResponseList
                (teamPlayersResponse.getPlayers(), teamPlayersResponse.getTeam(), date, currencyRates);
    }

    public List<PlayerFeeResponse> calculateAllPlayerFees(LocalDate date, Pageable pageable) throws PTSClientException {
        List<PlayerTeamDataResponse> playerTeamDataResponse = ptsClient.retrievePlayersAndTeamData(pageable)
                .orElseThrow(() -> new PTSClientException("No team players data retrieved from Player Team service."));
        CurrencyRatesResponse currencyRates = currencyClient.retrieveCurrencyRates(Constants.DATE_FORMATTER.format(date));

        return feeHelper.buildPlayerFeeResponseList(playerTeamDataResponse, date, currencyRates);
    }

}
