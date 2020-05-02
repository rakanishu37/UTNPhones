package com.utnphones.utnPhones.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Table(name = "calls")
public class Call {

    @Id
    private Integer id;

    private PhoneLine phoneFrom;

    private PhoneLine phoneTo;

    private Invoice invoice;

    private Fare fare;

    private Integer duration;

    private Float totalPrice;

    //private LocalDateTime date;
    private Instant date;

}
