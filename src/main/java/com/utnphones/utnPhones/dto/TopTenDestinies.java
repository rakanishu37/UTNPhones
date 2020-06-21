package com.utnphones.utnPhones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TopTenDestinies {
    private List<DestinyCallsCountDTO> list;

    public static TopTenDestinies fromList(List<DestinyCallsCountDTO> topTen){
        return TopTenDestinies.builder()
                .list(topTen)
                .build();
    }

}
