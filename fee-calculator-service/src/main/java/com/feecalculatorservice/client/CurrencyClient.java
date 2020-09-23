package com.feecalculatorservice.client;

import com.model.CurrencyRatesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springfox.documentation.annotations.Cacheable;

@FeignClient(value = "currency-service", url = "https://api.exchangeratesapi.io/")
public interface CurrencyClient {

    @GetMapping("/{date}")
    @Cacheable("currencyRates")
    CurrencyRatesResponse retrieveCurrencyRates(@PathVariable String date);

}
