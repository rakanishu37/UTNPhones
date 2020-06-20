package com.utnphones.utnPhones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceByClientDTO {
    private Integer idInvoice;
    private String phoneLineNumber;
    private Integer numberOfCalls;
    private Float priceCost;
    private Date invoiceDate;
    private Date dueDate;
    private Float totalPrice;
    private Boolean paid;
    private String firstName;
    private String lastName;
}
