package com.feecalculatorservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TeamPlayersFeeResponse {
    private PlayerFeeResponse playerFeeData;
    private String team;
}
