package com.model;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponse extends BaseResponse {
    Integer id;
    String name;
    String currency;
}
