package com.utnphones.utnPhones.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
//@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "provinces")
public class Province {
    //@Id
   // @GeneratedValue(strategy= GenerationType.AUTO)
   // @Column(name = "id_province")
    private Integer id;
    @NotNull
    private String name;

    public Province(Integer id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }
}
