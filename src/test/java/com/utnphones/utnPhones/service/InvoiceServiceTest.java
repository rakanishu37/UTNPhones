package com.utnphones.utnPhones.service;

import com.utnphones.utnPhones.domain.Invoice;
import com.utnphones.utnPhones.exceptions.InvoiceNotFoundException;
import com.utnphones.utnPhones.repository.InvoiceRepository;
import com.utnphones.utnPhones.services.InvoiceService;
import com.utnphones.utnPhones.testUtils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class InvoiceServiceTest {
    @Mock
    private InvoiceRepository invoiceRepository;

    private InvoiceService invoiceService;
    @Before
    public void setUp(){
        initMocks(this);
        this.invoiceService = new InvoiceService(invoiceRepository);
    }

    @Test
    public void testGetAllOk(){
        List<Invoice> invoiceList = TestUtils.getInvoices();
        when(this.invoiceRepository.findAll()).thenReturn(invoiceList);

        List<Invoice> invoiceListTest = this.invoiceService.getAll();

        Assert.assertEquals(invoiceList.size(), invoiceListTest.size());
    }

    @Test
    public void testCreateOk(){
        Invoice invoice = TestUtils.getInvoices().get(0);
        when(this.invoiceRepository.save(invoice)).thenReturn(invoice);

        Invoice invoiceTest = this.invoiceService.create(invoice);

        Assert.assertEquals(invoice, invoiceTest);
    }

    @Test
    public void testGetByIdOk() throws InvoiceNotFoundException {
        Invoice invoice = TestUtils.getInvoices().get(0);
        when(this.invoiceRepository.findById(invoice.getIdInvoice())).thenReturn(java.util.Optional.of(invoice));

        Invoice invoiceTest = this.invoiceService.getById(invoice.getIdInvoice());

        Assert.assertEquals(invoice, invoiceTest);
    }

    @Test(expected = InvoiceNotFoundException.class)
    public void testGetByIdNotFound() throws InvoiceNotFoundException {
        when(this.invoiceRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(null));
        this.invoiceService.getById(1);

    }

    @Test
    public void testGetAllByClient(){
        List<Invoice> invoiceList = TestUtils.getInvoices();
        when(this.invoiceRepository.getInvoicesByClient(1)).thenReturn(invoiceList);
    }
}
