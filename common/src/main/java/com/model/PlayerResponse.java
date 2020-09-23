package com.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.util.Constants;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class PlayerResponse extends BaseResponse {
    private String name;
    private String surname;
    private Status status;
    private Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT)
    private LocalDate hiringDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT)
    private LocalDate birthDate;
}
