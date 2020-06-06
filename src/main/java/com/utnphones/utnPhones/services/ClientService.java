package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.exceptions.ClientIsAlreadyDeletedException;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.projections.InvoicesDates;
import com.utnphones.utnPhones.repository.CallRepository;
import com.utnphones.utnPhones.repository.ClientRepository;
import com.utnphones.utnPhones.repository.InvoiceRepository;
import com.utnphones.utnPhones.repository.PhoneLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private ClientRepository clientRepository;

    private PhoneLineRepository phoneLineRepository;
    private CallRepository callRepository;
    private InvoiceRepository invoiceRepository;


    @Autowired
    public ClientService(ClientRepository clientRepository,PhoneLineRepository phoneLineRepository,CallRepository callRepository,InvoiceRepository invoiceRepository) {
        this.clientRepository = clientRepository;
        this.phoneLineRepository = phoneLineRepository;
        this.callRepository = callRepository;
        this.invoiceRepository = invoiceRepository;
    }

    public List<Client> getAll(Integer page){
        Pageable pageable = PageRequest.of(page, 16);
        return this.clientRepository.findAll(pageable).toList();
    }

    public Client create(Client client){
        return this.clientRepository.save(client);
    }

    public PhoneLine setPhoneline(PhoneLine phoneLine){
        return phoneLineRepository.save(phoneLine);
    }

    public Client getById(Integer id) throws ClientNotFoundException {
        return this.clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException());
    }

    public Client update(Integer idclient, Client modifiedClient) throws ClientNotFoundException {
		Client outdatedClient = clientRepository.findById(idclient)
            .orElseThrow(ClientNotFoundException::new);

		Client updated = updateClient(outdatedClient, modifiedClient);
		return clientRepository.save(updated);
    }

    private Client updateClient (Client oldClient, Client modifiedClient){
        oldClient.setFirstname(modifiedClient.getFirstname());
        oldClient.setSurname(modifiedClient.getSurname());
        oldClient.setDNI(modifiedClient.getDNI());
        oldClient.setCity(modifiedClient.getCity());
        oldClient.setPassword(modifiedClient.getPassword());
        oldClient.setUsername(modifiedClient.getUsername());
        return oldClient;
    }

    public Integer delete(Integer idClient) throws ClientNotFoundException, ClientIsAlreadyDeletedException {
        Client clientToBeDeleted = getById(idClient);
        if(!clientToBeDeleted.getIsActive()){
            throw new ClientIsAlreadyDeletedException();
        }
        return clientRepository.deleteClient(clientToBeDeleted.getId());
    }



    public List<InvoicesDates> getInvoicesBetweenDates(Integer clientId, Date from, Date to){
        return invoiceRepository.getByIdClientDateBetween(clientId,from,to);
    }
}
