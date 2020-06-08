package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.InvoiceMySQLDao;
import com.utnphones.utnPhones.domain.Invoice;
import com.utnphones.utnPhones.projections.InvoicesDates;
import com.utnphones.utnPhones.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    private InvoiceRepository invoiceRepository;


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

    public Invoice getById(Integer id) throws Exception {
        return this.invoiceRepository.findById(id).orElseThrow(Exception::new);
    }

    public List<InvoicesDates> getInvoicesBetweenDates(Integer idClient, Date dateFrom, Date dateTo) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String from = simpleDateFormat.format(dateFrom);
        String to = simpleDateFormat.format(dateTo);
        return invoiceRepository.getByIdClientDateBetween(idClient,from,to);
    }
}
