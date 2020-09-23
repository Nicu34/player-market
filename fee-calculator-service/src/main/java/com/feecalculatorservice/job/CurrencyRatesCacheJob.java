package com.feecalculatorservice.job;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class CurrencyRatesCacheJob implements ApplicationRunner {

    private CurrencyRatesCacheJobHelper currencyRatesCacheJobHelper;

    @Scheduled(cron = "0 0 0 * * *")
    public void populateCacheAndEvictDayBefore() {
        currencyRatesCacheJobHelper.populateCacheAndEvictDayBefore();
    }

    @Override
    public void run(ApplicationArguments args) {
        populateCacheAndEvictDayBefore();
    }
}
