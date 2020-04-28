package com.utnphones.utnPhones.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "clients")
public class Client {
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
    @OneToMany(mappedBy = "client")
    private List<PhoneLine> phoneLines;
}
