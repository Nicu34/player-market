package com.feecalculatorservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerFeeResponse {
    private String name;
    private String surname;
    private String team;
    private double transferFee;
    private double teamCommission;
    private double contractFee;
    private String currency;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT)
    private LocalDate date;
}
