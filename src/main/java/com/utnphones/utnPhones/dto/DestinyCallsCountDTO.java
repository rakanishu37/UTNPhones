package com.utnphones.utnPhones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DestinyCallsCountDTO {
    private String cityName;
    private Integer numberOfCalls;
}
