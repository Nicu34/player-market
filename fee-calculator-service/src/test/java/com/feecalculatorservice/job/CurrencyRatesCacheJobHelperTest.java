package com.feecalculatorservice.job;

import com.feecalculatorservice.client.CurrencyClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class CurrencyRatesCacheJobHelperTest {

    @Mock
    private CurrencyClient currencyClient;

    @InjectMocks
    private CurrencyRatesCacheJobHelper cacheJobHelper;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void populateCacheAndEvictDayBefore() {
        when(currencyClient.retrieveCurrencyRates(anyString()))
                .thenReturn(null);

        cacheJobHelper.populateCacheAndEvictDayBefore();

        verify(currencyClient).retrieveCurrencyRates(anyString());
    }
}