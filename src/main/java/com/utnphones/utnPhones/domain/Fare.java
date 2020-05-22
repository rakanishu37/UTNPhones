package com.utnphones.utnPhones.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "fares")
public class Fare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fare")
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_city_from")
    private City cityFrom;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_city_to")
    private City cityTo;

    @NotNull
    @Column(name = "price")
    private Float price;
}
