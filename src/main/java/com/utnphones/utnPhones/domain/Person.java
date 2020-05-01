package com.utnphones.utnPhones.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "clients")
public abstract class Person {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id_client")
    private Integer id;
    @NotNull
    private String firstname;
    @NotNull
    private String surname;
    @NotNull
    private City city;
    @NotNull
    private String DNI;
    @NotNull
    private String username;
    @NotNull
    private String password;


}
