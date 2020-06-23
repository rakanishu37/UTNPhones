package com.utnphones.utnPhones.controller;

import com.utnphones.utnPhones.controllers.InvoiceController;
import com.utnphones.utnPhones.projections.InvoiceByClient;
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

public class InvoiceControllerTest {
    @Mock
    private InvoiceService invoiceService;

    private InvoiceController invoiceController;

    @Before
    public void setUp(){
        initMocks(this);
        this.invoiceController = new InvoiceController(invoiceService);
    }

    @Test
    public void testGetAllByClientNoDatesOk() throws ParseException {
        List<InvoiceByClient> invoiceList = TestUtils.getInvoicesByClient();
        when(this.invoiceService.getInvoicesByClient(1, null, null)).thenReturn(invoiceList);

        List<InvoiceByClient> invoiceListTest = this.invoiceController.getInvoicesByClient(1, null, null);

        Assert.assertEquals(invoiceList.size(), invoiceListTest.size());
    }

    @Test
    public void testGetAllByClientWithDatesOk() throws ParseException {
        List<InvoiceByClient> invoiceList = TestUtils.getInvoicesByClient();
        when(this.invoiceService.getInvoicesByClient(1, "2020-05-05", "2020-05-30")).thenReturn(invoiceList);

        List<InvoiceByClient> invoiceListTest = this.invoiceController.getInvoicesByClient(1, "2020-05-05", "2020-05-30");

        Assert.assertEquals(invoiceList.size(), invoiceListTest.size());
    }

    @Test(expected = ParseException.class)
    public void testGetAllByClientWithDatesWrongFormat() throws ParseException {

        when(this.invoiceService.getInvoicesByClient(1, "2020-05-505", "2020-05-530")).thenThrow(new ParseException("", 1));

        this.invoiceService.getInvoicesByClient(1, "2020-05-505", "2020-05-530");

    }
}
