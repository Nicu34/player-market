package com.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerTeamDataResponse extends BaseResponse {
    private PlayerResponse player;
    private TeamResponse team;
}
