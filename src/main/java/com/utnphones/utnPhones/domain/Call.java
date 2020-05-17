package com.utnphones.utnPhones.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "calls")
public class Call {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_call")
    private Integer id;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_phone_line_from")
    private PhoneLine phoneFrom;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_phone_line_to")
    private PhoneLine phoneTo;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_invoice", insertable=false, updatable =false)
    @JsonBackReference
    private Invoice invoice;

    @Column(name = "fare")
    private Float fare;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "total_price")
    private Float totalPrice;

    @Column(name = "date_call")
    private Date date;

}
