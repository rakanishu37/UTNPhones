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
@Entity
@Table(name = "persons")
public class Person {
    @Id
    @Column(name = "id_person")
    private Integer id;
    @NotNull
    @Column(name = "firstname")
    private String firstname;
    @NotNull
    @Column(name = "surname")
    private String surname;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_city")
    private City city;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_user_type")
    private UserType userType;

    @NotNull
    @Column(name = "DNI")
    private String DNI;
    @NotNull
    @Column(name = "username")
    private String username;
    @NotNull
    @Column(name = "password")
    private String password;


}
