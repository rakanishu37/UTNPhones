package com.utnphones.utnPhones.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class City {
    @Id
    private Integer id;
    @NotNull
    @ManyToOne
    private Province province;
    @NotNull
    @Column(name = "city_name")
    private String name;
}
