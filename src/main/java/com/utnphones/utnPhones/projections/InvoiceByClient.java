package com.utnphones.utnPhones.projections;

import com.utnphones.utnPhones.domain.Invoice;

import java.util.Date;
import java.util.List;

public interface InvoiceByClient {
    Integer getIdInvoice();
    String getPhoneLineNumber();
    Integer getNumberOfCalls();
    Float getPriceCost();
    Date getInvoiceDate();
    Date getDueDate();
    Float getTotalPrice();
    Boolean getPaid();
    String getFirstName();
    String getLastName();

}
