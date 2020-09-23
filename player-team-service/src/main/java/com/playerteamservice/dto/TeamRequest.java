package com.playerteamservice.dto;

import lombok.*;
import validator.ValidCurrency;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class TeamRequest {
    @NotBlank
    String name;
    @ValidCurrency
    String currency;
}
