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

    void setIdInvoice(Integer idInvoice);
    void setPhoneLineNumber(String phoneLineNumber);
    void setNumberOfCalls(Integer numberOfCalls);
    void setPriceCost(Float priceCost);
    void setInvoiceDate(Date invoiceDate);
    void setDueDate(Date dueDate);
    void setTotalPrice(Float totalPrice);
    void setPaid(Boolean paid);
    void setFirstName(String firstName);
    void setLastName(String lastName);


}
