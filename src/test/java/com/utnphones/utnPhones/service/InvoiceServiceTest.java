package com.utnphones.utnPhones.service;

import com.utnphones.utnPhones.domain.Invoice;
import com.utnphones.utnPhones.exceptions.InvoiceNotFoundException;
import com.utnphones.utnPhones.projections.InvoiceByClient;
import com.utnphones.utnPhones.repository.InvoiceRepository;
import com.utnphones.utnPhones.services.InvoiceService;
import com.utnphones.utnPhones.testUtils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.text.ParseException;
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
    public void testGetAllByClientNoDatesOk() throws ParseException {
        List<InvoiceByClient> invoiceList = TestUtils.getInvoicesByClient();
        when(this.invoiceRepository.getInvoicesByClient(1)).thenReturn(invoiceList);

        List<InvoiceByClient> invoiceListTest = this.invoiceService.getInvoicesByClient(1, null, null);

        Assert.assertEquals(invoiceList.size(), invoiceListTest.size());
    }

    @Test
    public void testGetAllByClientWithDatesOk() throws ParseException {
        List<InvoiceByClient> invoiceList = TestUtils.getInvoicesByClient();
        when(this.invoiceRepository.getInvoicesByClientBetweenDates(1, "2020-05-05", "2020-05-30")).thenReturn(invoiceList);

        List<InvoiceByClient> invoiceListTest = this.invoiceService.getInvoicesByClient(1, "2020-05-05", "2020-05-30");

        Assert.assertEquals(invoiceList.size(), invoiceListTest.size());
    }

    @Test(expected = ParseException.class)
    public void testGetAllByClientWithDatesWrongFormat() throws ParseException {
        
        when(this.invoiceRepository.getInvoicesByClientBetweenDates(1, "2020-05-05", "2020-05-30")).thenReturn(null);

        this.invoiceService.getInvoicesByClient(1, "2020-05-505", "2020-05-530");

    }
}
