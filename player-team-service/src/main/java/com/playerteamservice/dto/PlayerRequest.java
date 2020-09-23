package com.playerteamservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.util.Constants;
import lombok.*;
import validator.ValidDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class PlayerRequest {
    @NotBlank(message = "Name cannot be blank.")
    private String name;
    @NotBlank(message = "Surname cannot be blank")
    private String surname;
    @Min(value = 0, message = "Team id should be greater than 0.")
    @Max(value = Integer.MAX_VALUE, message = "Team id should be not greater than " + Integer.MAX_VALUE)
    private Integer teamId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT)
    @ValidDate
    private LocalDate hiringDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT)
    @ValidDate
    private LocalDate birthDate;
}
