package com.playerteamservice.converter;

import com.model.BaseResponse;
import com.playerteamservice.entity.BaseEntity;

import java.util.List;
import java.util.stream.Collectors;

public abstract class GenericResponseConverter<IN extends BaseEntity, OUT extends BaseResponse> {
    public abstract OUT convert(IN source);

    public List<OUT> convertFrom(List<IN> collection) {
        return collection.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
