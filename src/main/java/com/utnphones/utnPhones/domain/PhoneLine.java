package com.utnphones.utnPhones.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "phone_lines")
public class PhoneLine {
    @Id
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_client")
    private Person client;

    private int number;
}
