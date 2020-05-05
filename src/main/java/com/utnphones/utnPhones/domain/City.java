package com.utnphones.utnPhones.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cities")
public class City {
    @Id
    @Column(name = "id_city")
    private Integer id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_province")
    private Province province;
    @NotNull
    @Column(name = "city_name")
    private String name;
    @NotNull
    @Column(name = "prefix")
    private String prefix;
}
