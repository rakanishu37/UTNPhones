package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.InvoiceMySQLDao;
import org.springframework.beans.factory.annotation.Autowired;

public class InvoiceService {
    private InvoiceMySQLDao invoiceMySQLDao;

    @Autowired
    public InvoiceService(final InvoiceMySQLDao invoiceMySQLDao) {
        this.invoiceMySQLDao = invoiceMySQLDao;
    }
}
