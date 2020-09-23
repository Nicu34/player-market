package com.playerteamservice.converter;

import com.model.PlayerResponse;
import com.playerteamservice.entity.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerResponseConverter extends GenericResponseConverter<Player, PlayerResponse> {

    @Override
    public PlayerResponse convert(Player source) {
        return PlayerResponse.builder()
                .name(source.getName())
                .surname(source.getSurname())
                .status(source.getStatus())
                .id(source.getId())
                .hiringDate(source.getHiringDate())
                .birthDate(source.getBirthDate())
                .build();
    }
}
