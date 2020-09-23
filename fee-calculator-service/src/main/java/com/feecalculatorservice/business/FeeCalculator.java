package com.feecalculatorservice.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Component
@AllArgsConstructor
public class FeeCalculator {

    private Random random;

    public double calculateTransferFee(LocalDate playerHiringDate, LocalDate playerBirthDate, LocalDate currentDate, double rate) {
        long monthsOfExperience = ChronoUnit.MONTHS.between(playerHiringDate, currentDate);
        if (monthsOfExperience == 0) {
            monthsOfExperience = 1;
        }
        long age = ChronoUnit.YEARS.between(playerBirthDate, currentDate);
        if (age == 0) {
            age = 1;
        }
        return monthsOfExperience * 100000f / age * rate;
    }

    public double calculateTeamCommission(double transferFee, double rate) {
        int randomPercentage = random.ints(1, 11)
                .findFirst()
                .orElse(10);

        return randomPercentage / 100f * transferFee * rate;
    }

    public double calculateContractFee(double transferFee, double teamCommission, double rate) {
        return transferFee + teamCommission * rate;
    }

}
