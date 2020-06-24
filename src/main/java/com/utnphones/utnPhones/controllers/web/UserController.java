package com.utnphones.utnPhones.controllers.web;


import com.utnphones.utnPhones.controllers.CallController;
import com.utnphones.utnPhones.controllers.ClientController;
import com.utnphones.utnPhones.controllers.InvoiceController;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.dto.TopTenDestinies;
import com.utnphones.utnPhones.dto.TotalPriceDTO;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.exceptions.UserNotLoggedException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.projections.InvoiceByClient;
import com.utnphones.utnPhones.projections.TotalPrice;
import com.utnphones.utnPhones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    private ClientController clientController;
    private CallController callController;
    private InvoiceController invoiceController;
    private SessionManager sessionManager;

    
    @Autowired
    public UserController(ClientController clientController,CallController callController,SessionManager sessionManager,InvoiceController invoiceController) {
        this.clientController = clientController;
        this.invoiceController= invoiceController;
        this.callController = callController;
        this.sessionManager= sessionManager;
    }

    @GetMapping("/me/calls")
    public ResponseEntity<Map<String, List<CallsDates>>> getCallsBetweenDates(@RequestHeader("Authorization") String token,
                                                                              @RequestParam(name = "dateFrom") String dateFrom,
                                                                              @RequestParam(name = "dateTo") String dateTo) throws ParseException, UserNotLoggedException{

        Integer idClient = sessionManager.getCurrentUser(token).getId();
        Map<String, List<CallsDates>> callsBetweenDates = callController.getAllByClient(idClient, dateFrom, dateTo);

        return (!callsBetweenDates.isEmpty()) ? ResponseEntity.ok(callsBetweenDates) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/me/invoices")
    public ResponseEntity<List<InvoiceByClient>> getInvoicesBetweenDates(@RequestHeader("Authorization") String token,
                                                                         @RequestParam(name = "dateFrom") String dateFrom,
                                                                         @RequestParam(name = "dateTo") String dateTo) throws ParseException, UserNotLoggedException{


        Integer idClient = sessionManager.getCurrentUser(token).getId();
        List<InvoiceByClient> invoices = invoiceController.getInvoicesByClient(idClient, dateFrom, dateTo);
        return (invoices.size() > 0) ? ResponseEntity.ok(invoices) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/me/calls/topten")
    public ResponseEntity<TopTenDestinies> getTopTenDestiniesByClient(@RequestHeader("Authorization") String token) throws UserNotLoggedException {
        Integer idClient = sessionManager.getCurrentUser(token).getId();
        TopTenDestinies topTenDestinies = this.callController.getTopTenDestiniesByClient(idClient);
        return (topTenDestinies.getList().size() > 0) ? ResponseEntity.ok(topTenDestinies) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/me/calls/totalprice")
    public ResponseEntity<TotalPrice> getTotalPrice(@RequestHeader("Authorization") String token) throws UserNotLoggedException {
        Integer idClient = sessionManager.getCurrentUser(token).getId();
        TotalPrice totalPriceDTO = this.clientController.getTotalPrice(idClient);
        return (totalPriceDTO.getTotalPrice() != null) ? ResponseEntity.ok(totalPriceDTO) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
