package com.feecalculatorservice.job;

import com.feecalculatorservice.client.CurrencyClient;
import com.util.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;

@Component
@AllArgsConstructor
@Slf4j
public class CurrencyRatesCacheJobHelper {

    private CurrencyClient currencyClient;

    @Retryable(value = Exception.class, maxAttempts = 10, backoff = @Backoff(delay = 100))
    public void populateCacheAndEvictDayBefore() {
        LocalDate date = LocalDate.now(ZoneOffset.UTC);
        String formattedDate = date.format(Constants.DATE_FORMATTER);

        log.info("Preparing to populate currency rates cache for date {}.", formattedDate);
        currencyClient.retrieveCurrencyRates(formattedDate);
        log.info("Successfully populated cache.");
        clearCurrencyRatesCache(date.minusDays(1).format(Constants.DATE_FORMATTER));
        log.info("Successfully evicted cache.");
    }

    /**
     * Evict cache from specific day.
     */
    @CacheEvict(value = "currencyRates", key = "#date")
    public void clearCurrencyRatesCache(String date) {
    }
}
