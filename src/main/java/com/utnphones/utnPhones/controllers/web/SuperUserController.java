package com.utnphones.utnPhones.controllers.web;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.Person;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.exceptions.ClientIsAlreadyDeletedException;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.exceptions.UnauthorizedAccessException;
import com.utnphones.utnPhones.exceptions.UserNotLoggedException;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/superuser")
public class SuperUserController {

    /**
     * 2) Manejo de clientes.
     * 3) Alta , baja y suspensión de líneas.
     * 4) Consulta de tarifas.
     * 5) Consulta de llamadas por usuario.
     * 6) Consulta de facturación .
     */

    @GetMapping("/{page}")
    public ResponseEntity<List<Call>> getAll(@RequestHeader("Authorization") String token, @PathVariable Integer page) throws UserNotLoggedException {
        Person person = this.sessionManager.getCurrentUser(token);

        List<Call> calls = this.callService.getAll(page);
        return (calls.size() > 0) ? ResponseEntity.ok(calls) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //TODO
    //Paginacion para esto, un dto con el id del cliente y que pagina y cantidad de registros a devolver
    /*@GetMapping("/")  calls?idClient=654
    public List<Call> getAllByClient(){
        return callService.getAllByClient();
    }*/


    @GetMapping("/")
    public ResponseEntity<List<Client>> getAll(@RequestHeader("Authorization") String token) throws UserNotLoggedException, UnauthorizedAccessException {
        List<Client> list = clientService.getAll();
        return (list.size() > 0) ? ResponseEntity.ok(list) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{idClient}")
    public ResponseEntity<Client> getById(@PathVariable Integer idClient) throws ClientNotFoundException {
        return ResponseEntity.ok(clientService.getById(idClient));
    }


    @PostMapping("/")
    public ResponseEntity<Client> create(@RequestBody Client client) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(client));
    }

    @PostMapping("/{idClient}/phonelines")
    public ResponseEntity<PhoneLine> createPhoneLine(@PathVariable Integer idClient, @RequestBody PhoneLine phoneLine) throws ClientNotFoundException {
        phoneLine.setClient(clientService.getById(idClient));
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.setPhoneline(phoneLine));
    }

    @PutMapping("/{idClient}")
    public ResponseEntity<Client> updateClient(@PathVariable Integer idClient, @RequestBody Client updatedClient) throws ClientNotFoundException {
        return ResponseEntity.ok(clientService.update(idClient,updatedClient));
    }

    @DeleteMapping("/{idClient}")
    public ResponseEntity<Integer> deleteClient(@PathVariable Integer idClient) throws ClientNotFoundException, ClientIsAlreadyDeletedException {
        return ResponseEntity.ok(clientService.delete(idClient));
    }

    @GetMapping("/")
    public List<PhoneLine> getAll(){
        return this.phoneLineService.getAll();
    }

    @PostMapping("/")
    public PhoneLine create(@RequestBody PhoneLine phoneLine){
        return this.phoneLineService.create(phoneLine);
    }

    @GetMapping("/{idPhoneLine}")
    public PhoneLine getById(@PathVariable Integer idPhoneLine) throws PhoneLineNotFoundException {
        return this.phoneLineService.getById(idPhoneLine);
    }

    @PutMapping("/{idPhoneline}/")
    public ResponseEntity<PhoneLine> updatePhoneLine(@PathVariable Integer idPhoneline, @RequestBody PhoneLine phoneLine) throws PhoneLineNotFoundException{
        phoneLine.setClient(phoneLineService.getById(idPhoneline).getClient());
        return ResponseEntity.ok(phoneLineService.updatePhoneLine(phoneLine));
    }


    client/5
    //Todo acomodar
    private URI getLocation(Message message) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(message.getMessageId())
                .toUri();
    }
}
