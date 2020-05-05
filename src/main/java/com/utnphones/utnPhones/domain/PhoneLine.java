package com.utnphones.utnPhones.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "phone_lines")
public class PhoneLine {
    @Id
    @Column(name = "id_phone_line")
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_line_type")
    private LineType lineType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Person client;

    @NotNull
    @Column(name = "line_number")
    private String number;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LineStatus lineStatus;


}
