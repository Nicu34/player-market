package com.feecalculatorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FeeCalculatorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeeCalculatorServiceApplication.class, args);
	}

}
