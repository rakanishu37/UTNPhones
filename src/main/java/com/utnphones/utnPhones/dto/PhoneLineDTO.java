package com.utnphones.utnPhones.dto;

import com.utnphones.utnPhones.domain.LineStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class PhoneLineDTO {

    @NotNull
    private String cityName;

    @NotNull
    private String lineNumber;

    @NotNull
    private String lineType;

    @NotNull
    private LineStatus status;
}




