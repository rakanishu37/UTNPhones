package com.utnphones.utnPhones.projections;

import java.util.Date;

public interface InvoicesDates {
    String getPhoneLine();
    Long getNumberOfCalls();
    Float getPriceCost();
    Float getTotalPrice();
    Date getInvoiceDate();
    Date getDueDate();
}
