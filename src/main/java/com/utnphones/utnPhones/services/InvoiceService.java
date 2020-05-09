package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.InvoiceMySQLDao;
import com.utnphones.utnPhones.domain.Invoice;
import com.utnphones.utnPhones.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Invoice> getAll(){
        return this.invoiceRepository.findAll();
    }

    public Invoice create(Invoice invoice){
        return this.invoiceRepository.save(invoice);
    }
}
