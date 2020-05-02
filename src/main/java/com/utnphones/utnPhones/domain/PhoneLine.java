package com.utnphones.utnPhones.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "phone_lines")
public class PhoneLine {
    @Id
    private Integer id;
    /*@ManyToOne
    @JoinColumn(name = "id_client")*/
    private LineType lineType;

    private Person client;

    private String number;

    private LineStatus lineStatus;

    @Override
    public String toString() {
        return "PhoneLine{" +
                "id=" + id +
                ", lineType=" + lineType +
                ", number='" + number + '\'' +
                ", lineStatus=" + lineStatus.toString() +
                '}';
    }
}
