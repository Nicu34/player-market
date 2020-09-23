package com.feecalculatorservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.util.Constants;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TeamFeeRequest {
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT)
    private Date currentDate;
}
