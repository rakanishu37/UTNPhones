package com.utnphones.utnPhones.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "calls")
public class Call {

    @Id
    @Column(name = "id_call")
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", insertable=false, updatable =false)
    private PhoneLine phoneFrom;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", insertable=false, updatable =false)
    private PhoneLine phoneTo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Invoice invoice;

    private Float fare;

    private Integer duration;

    private Float totalPrice;

    //private LocalDateTime date;
    private Instant date;

}
