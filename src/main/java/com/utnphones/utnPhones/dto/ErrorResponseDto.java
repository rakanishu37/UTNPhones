package com.utnphones.utnPhones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
    int code;
    String description;
}
