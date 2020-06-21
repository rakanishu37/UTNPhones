package com.utnphones.utnPhones.dto;

import com.utnphones.utnPhones.projections.DestinyQuantity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TopTenDestinies {
    private List<DestinyQuantity> list;

    public static TopTenDestinies fromList(List<DestinyQuantity> topTen){
        return TopTenDestinies.builder()
                .list(topTen)
                .build();
    }

}
