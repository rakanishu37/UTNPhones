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
public class InvoiceDatesDTO {
    private String phoneLine;
    private Long numberOfCalls;
    private Float priceCost;
    private Float totalPrice;
    private Date invoiceDate;
    private Date dueDate;
}
