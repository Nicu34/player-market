package com.feecalculatorservice.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.MockitoAnnotations.initMocks;

class FeeCalculatorTest {

    private static final int COMMISSION_PERCENTAGE = 5;
    private static final double TRANSFER_FEE = 1000;
    private static final double RATE = 1.5;

    @Mock
    private Random random;

    @InjectMocks
    private FeeCalculator feeCalculator;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @ParameterizedTest
    @MethodSource("transferFeeParameters")
    void calculateTransferFee(LocalDate currentDate, LocalDate hiringDate, LocalDate birthDate, final double expectedValue) {
        assertEquals(expectedValue, feeCalculator.calculateTransferFee(hiringDate, birthDate, currentDate, RATE));
    }

    @Test
    void calculateTeamCommission() {
        Mockito.when(random.ints(anyInt(), anyInt())).thenReturn(IntStream.of(COMMISSION_PERCENTAGE));
        final double expectedValue = COMMISSION_PERCENTAGE / 100f * TRANSFER_FEE * RATE;

        assertEquals(expectedValue, feeCalculator.calculateTeamCommission(TRANSFER_FEE, RATE));
    }

    @Test
    void calculateContractFee() {
        final double expectedValue = TRANSFER_FEE + COMMISSION_PERCENTAGE * RATE;

        assertEquals(expectedValue, feeCalculator.calculateContractFee(TRANSFER_FEE, COMMISSION_PERCENTAGE, RATE));
    }

    private static Stream<Arguments> transferFeeParameters() {
        return Stream.of(
                Arguments.of(LocalDate.of(2020, 9, 22),
                        LocalDate.of(2020, 7, 12),
                        LocalDate.of(2000, 1, 1),
                        2 * 100000f / 20 * RATE),
                Arguments.of(LocalDate.of(2020, 9, 22),
                        LocalDate.of(2020, 9, 12),
                        LocalDate.of(2000, 1, 1),
                        100000f / 20 * RATE),
                Arguments.of(LocalDate.of(2020, 9, 22),
                        LocalDate.of(2020, 7, 12),
                        LocalDate.of(2020, 8, 1),
                        2 * 100000f * RATE)
        );
    }
}