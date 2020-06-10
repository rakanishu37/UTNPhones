package com.utnphones.utnPhones.controllers.web;

import com.utnphones.utnPhones.controllers.CallController;
import com.utnphones.utnPhones.controllers.ClientController;
import com.utnphones.utnPhones.controllers.FareController;
import com.utnphones.utnPhones.controllers.InvoiceController;
import com.utnphones.utnPhones.controllers.PhoneLineController;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.Fare;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.dto.ClientCreatedDTO;
import com.utnphones.utnPhones.dto.ClientUpdatedDTO;
import com.utnphones.utnPhones.dto.PhoneLineDTO;
import com.utnphones.utnPhones.exceptions.CityNotFoundException;
import com.utnphones.utnPhones.exceptions.ClientIsAlreadyDeletedException;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.exceptions.LineTypeNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotIsAlreadyDeletedException;
import com.utnphones.utnPhones.exceptions.UnauthorizedAccessException;
import com.utnphones.utnPhones.exceptions.UserNotLoggedException;
import com.utnphones.utnPhones.exceptions.ValidationException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.projections.InvoiceByClient;
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

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/superuser")
public class SuperUserController {

    /**
     * 2) Manejo de clientes ok.
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
    public ResponseEntity<List<CallsDates>> getAllCalls(@RequestHeader("Authorization") String token,
                                                        @RequestParam(required = true, value = "from") Integer from,
                                                        @RequestParam(required = true, value = "quantity") Integer quantity,
                                                        @RequestParam(required = false, value = "dateFrom") String dateFrom,
                                                        @RequestParam(required = false, value = "dateTo") String dateTo) throws UserNotLoggedException, ParseException {
        System.out.println(dateFrom);
        System.out.println(dateTo);
        List<CallsDates> calls = this.callController.getAllRange(quantity, from, dateFrom, dateTo);
        return (calls.size() > 0) ? ResponseEntity.ok(calls) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /*@GetMapping("/")  calls?idClient=654
    public List<Call> getAllByClient(){
        return callService.getAllByClient();
    }*/



    @GetMapping("/calls/client/{idClient}")
    public ResponseEntity<Map<String, List<CallsDates>>> getAllCallsByClient(@RequestHeader("Authorization") String token, @PathVariable Integer idClient) throws ClientNotFoundException {
        Client client = this.clientController.getById(idClient); //para evitar la referencia circular lo dejamos aca
        Map<String, List<CallsDates>> calls = this.callController.getAllByClient(client.getId());
        return (!calls.isEmpty()) ? ResponseEntity.ok(calls) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClient(@RequestHeader("Authorization") String token,
                                                     @RequestParam(required = true, value = "from") Integer from,
                                                     @RequestParam(required = true, value = "quantity") Integer quantity) throws UserNotLoggedException, UnauthorizedAccessException {
        List<Client> list = this.clientController.getAll(quantity,from);
        return (list.size() > 0) ? ResponseEntity.ok(list) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/clients/{idClient}")
    public ResponseEntity<Client> getClientById(@PathVariable Integer idClient) throws ClientNotFoundException {
        return ResponseEntity.ok(clientController.getById(idClient));
    }


    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@RequestBody ClientCreatedDTO client) throws CityNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.clientController.create(client));
    }

    //TODO
    /*@PostMapping("/clients/{idClient}/phonelines")
    public ResponseEntity<PhoneLine> createPhoneLine(@PathVariable Integer idClient,
                                                     @Valid @RequestBody PhoneLineDTO phoneLineDTO) throws ClientNotFoundException, LineTypeNotFoundException {

        return ResponseEntity.status(HttpStatus.CREATED).body(this.phoneLineController.create(idClient,phoneLineDTO));
    }*/

    @PutMapping("/clients/{idClient}")
    public ResponseEntity<Client> updateClient(@PathVariable Integer idClient, @RequestBody ClientUpdatedDTO updatedClient) throws ClientNotFoundException, CityNotFoundException {
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
        return ResponseEntity.ok(phoneLineController.updatePhoneLine(idPhoneline,phoneLine));
    }*/

    @DeleteMapping("/phonelines/{idPhoneLine}")
    public ResponseEntity<?> deletePhoneLine(@PathVariable Integer idPhoneLine) throws PhoneLineNotFoundException, PhoneLineNotIsAlreadyDeletedException {
        this.phoneLineController.delete(idPhoneLine);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fares")
    public ResponseEntity<Fare> getFareByCities(@RequestParam(name = "cityFrom") Integer idCityFrom
                                                , @RequestParam(name = "cityTo") Integer idCityTo) throws CityNotFoundException {
        return ResponseEntity.ok(this.fareController.getFareByCities(idCityFrom, idCityTo));
    }

    @GetMapping("/clients/{idClient}/invoices")
    public ResponseEntity<List<InvoiceByClient>> getInvoicesByClient(@RequestHeader("Authorization") String token, @PathVariable Integer idClient) throws ClientNotFoundException {
        Client client = this.clientController.getById(idClient); //para evitar la referencia circular lo dejamos aca
        List<InvoiceByClient> invoiceByClient = this.invoiceController.getInvoicesByClient(idClient);
        return (invoiceByClient.size() > 0) ? ResponseEntity.ok(invoiceByClient) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
