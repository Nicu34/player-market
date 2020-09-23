package com.util;

import com.model.CurrencyRatesResponse;
import com.model.PlayerResponse;
import com.model.Status;
import com.model.TeamResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class TestUtils {
    public static final LocalDate DATE = LocalDate.of(2020, 1, 1);
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final int ID = 1234;
    public static final String EUR_CURRENCY = "EUR";
    public static final String GBP_CURRENCY = "GBP";
    public static final double CONVERSION_RATE = 1.42;
    public static final double FEE_AMOUNT = 1234.56;
    public static final double DEFAULT_CONVERSION_RATE = 1;
    public static final String MESSAGE = "MESSAGE";
    public static final String FIELD = "field";
    public static final String ERROR_MESSAGE = "Input parameters validation errors";

    public static PlayerResponse mockPlayerResponse() {
        return PlayerResponse.builder()
                .birthDate(DATE)
                .hiringDate(DATE)
                .name("name")
                .surname("surname")
                .id(123)
                .status(Status.HIRED)
                .build();
    }

    public static TeamResponse mockTeamResponse(String currency) {
        return TeamResponse.builder()
                .currency(currency)
                .name("name")
                .id(123)
                .build();
    }

    public static CurrencyRatesResponse mockCurrencyRatesResponse(Map<String, Double> currencyRatesMap) {
        return CurrencyRatesResponse.builder()
                .rates(currencyRatesMap)
                .build();
    }

}
