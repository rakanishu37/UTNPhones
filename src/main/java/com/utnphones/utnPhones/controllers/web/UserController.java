package com.utnphones.utnPhones.controllers.web;


import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.exceptions.ValidationException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.projections.InvoicesDates;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    //url lo que sea/me (el me significa que tenemos que scarlo de la session su info)
    /**
     * 2) Consulta de llamadas del usuario logueado por rango de fechas.
     * 3) Consulta de facturas del usuario logueado por rango de fechas.
     * 4) Consulta de TOP 10 destinos más llamados por el usuario.
     */
   /* @GetMapping("/me/calls")
    public ResponseEntity<Map<String, List<CallsDates>>> getCallsBetweenDates(@PathVariable Integer idClient,
                                                                              @RequestParam(name = "dateFrom") String dateFrom,
                                                                              @RequestParam(name = "dateTo") String dateTo) throws ClientNotFoundException, ParseException, ValidationException {
        Client client = this.clientService.getById(idClient);
        Date from = new SimpleDateFormat("yyyy/MM/dd").parse(dateFrom);
        Date to = new SimpleDateFormat("yyyy/MM/dd").parse(dateTo);
        Map<String, List<CallsDates>> callsBetweenDates = clientService.getCallsBetweenDates(client.getId(), from, to);

        return (!callsBetweenDates.isEmpty()) ? ResponseEntity.ok(callsBetweenDates) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/me/invoices")
    public ResponseEntity<List<InvoicesDates>> getInvoicesBetweenDates(@PathVariable Integer idClient,
                                                                       @RequestParam(name = "dateFrom") String dateFrom,
                                                                       @RequestParam(name = "dateTo") String dateTo) throws ClientNotFoundException, ParseException, ValidationException {

        Date from = new SimpleDateFormat("yyyy/MM/dd").parse(dateFrom);
        Date to = new SimpleDateFormat("yyyy/MM/dd").parse(dateTo);
        List<InvoicesDates> invoices = clientService.getInvoicesBetweenDates(idClient, from, to);
        return (!invoices.isEmpty()) ? ResponseEntity.ok(invoices) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }*/
}
