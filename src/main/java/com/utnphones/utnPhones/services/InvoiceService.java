package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.projections.InvoiceByClient;
import com.utnphones.utnPhones.repository.InvoiceRepository;
import com.utnphones.utnPhones.utils.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class InvoiceService {
    private InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(final InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<InvoiceByClient> getInvoicesByClient(Integer idClient, String dateFrom, String dateTo) throws ParseException {
        if(dateFrom==null || dateTo==null){
            return this.invoiceRepository.getInvoicesByClient(idClient);
        }else{
            return this.invoiceRepository.getInvoicesByClientBetweenDates(idClient, DateFormatUtil.formatDate(dateFrom), DateFormatUtil.formatDate(dateTo));
        }

    }
}
