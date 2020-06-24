package com.utnphones.utnPhones.controllers.web;

import com.utnphones.utnPhones.controllers.CallController;
import com.utnphones.utnPhones.controllers.ClientController;
import com.utnphones.utnPhones.controllers.FareController;
import com.utnphones.utnPhones.controllers.InvoiceController;
import com.utnphones.utnPhones.controllers.PhoneLineController;
import com.utnphones.utnPhones.domain.Call;
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
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.projections.InvoiceByClient;
import com.utnphones.utnPhones.utils.UriGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/backoffice")
public class BackOfficeController {
    private PhoneLineController phoneLineController;
    private FareController fareController;
    private ClientController clientController;
    private InvoiceController invoiceController;
    private CallController callController;

    @Autowired
    public BackOfficeController(PhoneLineController phoneLineController,
                                FareController fareController, ClientController clientController,
                                InvoiceController invoiceController, CallController callController) {
        this.phoneLineController = phoneLineController;
        this.fareController = fareController;
        this.clientController = clientController;
        this.invoiceController = invoiceController;
        this.callController = callController;
    }

    /*@GetMapping("/calls")
    public ResponseEntity<List<CallsDates>> getAllCalls(@RequestParam(value = "from") Integer from,
                                                        @RequestParam(value = "quantity") Integer quantity,
                                                        @RequestParam(required = false, value = "dateFrom") String dateFrom,
                                                        @RequestParam(required = false, value = "dateTo") String dateTo) throws ParseException {

        List<CallsDates> calls = this.callController.getAllRange(quantity, from, dateFrom, dateTo);
        return (calls.size() > 0) ? ResponseEntity.ok(calls) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }*/

    @GetMapping("/calls/client/{idClient}")
    public ResponseEntity<Map<String, List<CallsDates>>> getAllCallsByClient(@PathVariable Integer idClient,
                                                                             @RequestParam(required = false, value = "dateFrom") String dateFrom,
                                                                             @RequestParam(required = false, value = "dateTo") String dateTo) throws ClientNotFoundException, ParseException {
        Client client = this.clientController.getById(idClient); //para evitar la referencia circular lo dejamos aca
        Map<String, List<CallsDates>> calls = this.callController.getAllByClient(client.getId(),dateFrom,dateTo);
        return (!calls.isEmpty()) ? ResponseEntity.ok(calls) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClient(@RequestParam(required = true, value = "from") Integer from,
                                                     @RequestParam(required = true, value = "quantity") Integer quantity){
        List<Client> list = this.clientController.getAll(quantity, from);
        return (list.size() > 0) ? ResponseEntity.ok(list) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping("/clients/{idClient}")
    public ResponseEntity<Client> getClientById(@PathVariable Integer idClient) throws ClientNotFoundException {
        return ResponseEntity.ok(clientController.getById(idClient));
    }

    @PostMapping("/clients")
    public ResponseEntity<?> createClient(@RequestBody ClientCreatedDTO client) throws CityNotFoundException, NoSuchAlgorithmException {
        URI uri = UriGenerator.getLocation(clientController.create(client).getId());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/clients/{idClient}")
    public ResponseEntity<Client> updateClient(@PathVariable Integer idClient, @RequestBody ClientUpdatedDTO updatedClient) throws ClientNotFoundException, CityNotFoundException, NoSuchAlgorithmException {
        return ResponseEntity.ok(this.clientController.update(idClient, updatedClient));
    }

    @DeleteMapping("/clients/{idClient}")
    public ResponseEntity<?> deleteClient(@PathVariable Integer idClient) throws ClientNotFoundException, ClientIsAlreadyDeletedException {
        this.clientController.delete(idClient);
        return ResponseEntity.ok().build();
    }

    /*//todo borrar?
    @GetMapping("/phonelines/{idPhoneLine}")
    public ResponseEntity<PhoneLine> getPhonelineById(@PathVariable Integer idPhoneLine) throws PhoneLineNotFoundException {
        return ResponseEntity.ok(this.phoneLineController.getById(idPhoneLine));
    }*/


    @PostMapping("/clients/{idClient}/phonelines")
    public ResponseEntity<?> createPhoneLine(@PathVariable Integer idClient,
                                                     @Valid @RequestBody PhoneLineDTO phoneLineDTO) throws ClientNotFoundException, LineTypeNotFoundException, CityNotFoundException {

        URI uri = UriGenerator.getLocation(phoneLineController.create(idClient, phoneLineDTO).getId());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/phonelines/{idPhoneline}")
    public ResponseEntity<PhoneLine> updatePhoneLine(@PathVariable Integer idPhoneline,
                                                     @Valid @RequestBody PhoneLineDTO phoneLineDto)
            throws PhoneLineNotFoundException, LineTypeNotFoundException, CityNotFoundException {

        return ResponseEntity.ok(phoneLineController.updatePhoneLine(idPhoneline, phoneLineDto));
    }

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
    public ResponseEntity<List<InvoiceByClient>> getInvoicesByClient(@PathVariable Integer idClient,
                                                                     @RequestParam(required = false, value = "dateFrom") String dateFrom,
                                                                     @RequestParam(required = false, value = "dateTo") String dateTo) throws ClientNotFoundException, ParseException {
        Client client = this.clientController.getById(idClient);
        List<InvoiceByClient> invoiceByClient = this.invoiceController.getInvoicesByClient(idClient, dateFrom, dateTo);
        return (invoiceByClient.size() > 0) ? ResponseEntity.ok(invoiceByClient) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}