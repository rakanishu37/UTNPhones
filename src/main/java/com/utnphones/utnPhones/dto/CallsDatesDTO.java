package com.utnphones.utnPhones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CallsDatesDTO {
    private String cityOrigin;
    private String phoneNumberOrigin;
    private String cityDestiny;
    private String phoneNumberDestiny;
    private Integer duration;
    private Float totalPrice;
    private Date date;
}
