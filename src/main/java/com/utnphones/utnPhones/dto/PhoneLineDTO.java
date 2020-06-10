package com.utnphones.utnPhones.dto;

import com.utnphones.utnPhones.domain.LineStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PhoneLineDTO {

    @NotNull
    private String prefix;

    @NotNull
    private String lineNumber;

    @NotNull
    private String lineType;

    @NotNull
    private LineStatus status;
}




