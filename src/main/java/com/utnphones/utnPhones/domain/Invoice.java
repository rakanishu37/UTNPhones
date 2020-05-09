package com.utnphones.utnPhones.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "invoices")
public class Invoice {
    @Id
    @Column(name = "id_invoice")
    private Integer idInvoice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_line", insertable=false, updatable =false)
    private PhoneLine phoneLine;

    @Column(name = "number_of_calls")
    private Integer numberOfCalls;

    @Column(name = "price_cost")
    private Float priceCost;

    @Column(name = "total_price")
    private Float totalPrice;

    @Column(name = "invoice_date")
    private Date invoiceDate;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "paid")
    private Boolean paid;

    @OneToMany(mappedBy = "invoice")
    @JsonBackReference
    private List<Call> calls;
}
