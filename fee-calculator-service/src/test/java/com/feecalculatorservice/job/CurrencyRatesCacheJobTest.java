package com.feecalculatorservice.job;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class CurrencyRatesCacheJobTest {

    @Mock
    private CurrencyRatesCacheJobHelper currencyRatesCacheJobHelper;

    @InjectMocks
    private CurrencyRatesCacheJob currencyRatesCacheJob;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void populateCacheAndEvictDayBefore() {
        doNothing().when(currencyRatesCacheJobHelper).populateCacheAndEvictDayBefore();

        currencyRatesCacheJob.populateCacheAndEvictDayBefore();

        verify(currencyRatesCacheJobHelper).populateCacheAndEvictDayBefore();
    }

    @Test
    void run() {
        doNothing().when(currencyRatesCacheJobHelper).populateCacheAndEvictDayBefore();

        currencyRatesCacheJob.run(null);

        verify(currencyRatesCacheJobHelper).populateCacheAndEvictDayBefore();
    }
}