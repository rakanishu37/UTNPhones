package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.InvoiceMySQLDao;
import com.utnphones.utnPhones.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    private InvoiceMySQLDao invoiceMySQLDao;
    private InvoiceRepository invoiceRepository;

    /*@Autowired
    public InvoiceService(final InvoiceMySQLDao invoiceMySQLDao) {
        this.invoiceMySQLDao = invoiceMySQLDao;
    }*/
    @Autowired
    public InvoiceService(final InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }
}
