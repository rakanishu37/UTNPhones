package com.utnphones.utnPhones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientUpdatedDTO {
    @NotNull
    private String firstname;
    @NotNull
    private String surname;
    @NotNull
    private String cityName;
    @NotNull
    private String username;
    @NotNull
    private String password;
}
