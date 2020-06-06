package com.utnphones.utnPhones.controllers.web;

import com.utnphones.utnPhones.controllers.CallController;
import com.utnphones.utnPhones.controllers.ClientController;
import com.utnphones.utnPhones.controllers.FareController;
import com.utnphones.utnPhones.controllers.InvoiceController;
import com.utnphones.utnPhones.controllers.PhoneLineController;
import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.Fare;
import com.utnphones.utnPhones.domain.Person;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.dto.PageableResponse;
import com.utnphones.utnPhones.exceptions.ClientIsAlreadyDeletedException;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.exceptions.InvalidCityException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.exceptions.UnauthorizedAccessException;
import com.utnphones.utnPhones.exceptions.UserNotLoggedException;
import com.utnphones.utnPhones.projections.FarePriceBetweenCities;
import com.utnphones.utnPhones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/superuser")
public class SuperUserController {

    /**
     * 2) Manejo de clientes casi ok.
     * 3) Alta , baja y suspensión de líneas .
     * 4) Consulta de tarifas ok.
     * 5) Consulta de llamadas por usuario casi ok.
     * 6) Consulta de facturación .
     */

    private SessionManager sessionManager;
    private PhoneLineController phoneLineController;
    private FareController fareController;
    private ClientController clientController;
    private InvoiceController invoiceController;
    private CallController callController;

    @Autowired
    public SuperUserController(SessionManager sessionManager, PhoneLineController phoneLineController,
                               FareController fareController, ClientController clientController,
                               InvoiceController invoiceController, CallController callController) {
        this.sessionManager = sessionManager;
        this.phoneLineController = phoneLineController;
        this.fareController = fareController;
        this.clientController = clientController;
        this.invoiceController = invoiceController;
        this.callController = callController;
    }
    // todo cambiar a lo que dijo pablo, hacaer metodo dinamico para filtrado de fechas
    @GetMapping("/calls")
    public ResponseEntity<List<Call>> getAllCalls(@RequestHeader("Authorization") String token, @RequestParam("from") Integer from, @RequestParam("to") Integer to) throws UserNotLoggedException {
        List<Call> calls = this.callController.getAllRange(to, from);
        return (calls.size() > 0) ? ResponseEntity.ok(calls) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/calls/client/{idClient}")
    public ResponseEntity<List<Call>> getAllCallsByClient(@RequestHeader("Authorization") String token, @PathVariable Integer idClient){
        List<Call> calls = this.callController.getAllByClient(idClient);
        return (calls.size() > 0) ? ResponseEntity.ok(calls) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping("/clients/{page}")
    public ResponseEntity<List<Client>> getAllClient(@RequestHeader("Authorization") String token, @PathVariable Integer page) throws UserNotLoggedException, UnauthorizedAccessException {
        List<Client> list = this.clientController.getAll(page);
        return (list.size() > 0) ? ResponseEntity.ok(list) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/clients/{idClient}")
    public ResponseEntity<Client> getClientById(@PathVariable Integer idClient) throws ClientNotFoundException {
        return ResponseEntity.ok(clientController.getById(idClient));
    }


    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.clientController.create(client));
    }

    @PostMapping("/clients/{idClient}/phonelines")
    public ResponseEntity<PhoneLine> createPhoneLine(@PathVariable Integer idClient, @RequestBody PhoneLine phoneLine) throws ClientNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.phoneLineController.create(idClient,phoneLine));
    }

    @PutMapping("/clients/{idClient}")
    public ResponseEntity<Client> updateClient(@PathVariable Integer idClient, @RequestBody Client updatedClient) throws ClientNotFoundException {
        return ResponseEntity.ok(this.clientController.update(idClient,updatedClient));
    }

    @DeleteMapping("/clients/{idClient}")
    public ResponseEntity<Integer> deleteClient(@PathVariable Integer idClient) throws ClientNotFoundException, ClientIsAlreadyDeletedException {
        this.clientController.delete(idClient);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/phonelines/{idPhoneLine}")
    public ResponseEntity<PhoneLine> getPhonelineById(@PathVariable Integer idPhoneLine) throws PhoneLineNotFoundException {
        return ResponseEntity.ok(this.phoneLineController.getById(idPhoneLine));
    }
/*
    @PutMapping("/{idPhoneline}/")
    public ResponseEntity<PhoneLine> updatePhoneLine(@PathVariable Integer idPhoneline, @RequestBody PhoneLine phoneLine) throws PhoneLineNotFoundException{
        phoneLine.setClient(phoneLineService.getById(idPhoneline).getClient());
        return ResponseEntity.ok(phoneLineService.updatePhoneLine(phoneLine));
    }
*/

    @GetMapping("/fares")
    public ResponseEntity<Fare> getFareByCities(@RequestParam(name = "cityFrom") Integer idCityFrom
                                                , @RequestParam(name = "cityTo") Integer idCityTo) throws InvalidCityException {
        return ResponseEntity.ok(this.fareController.getFareByCities(idCityFrom, idCityTo));
    }

}
