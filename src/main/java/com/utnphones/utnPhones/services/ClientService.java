package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.dto.ClientCreatedDTO;
import com.utnphones.utnPhones.dto.ClientUpdatedDTO;
import com.utnphones.utnPhones.exceptions.CityNotFoundException;
import com.utnphones.utnPhones.exceptions.ClientIsAlreadyDeletedException;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.repository.ClientRepository;
import com.utnphones.utnPhones.repository.PhoneLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private ClientRepository clientRepository;
    private CityService cityService;

    private PhoneLineRepository phoneLineRepository; // todo revisar


    @Autowired
    public ClientService(ClientRepository clientRepository, PhoneLineRepository phoneLineRepository, CityService cityService) {
        this.clientRepository = clientRepository;
        this.phoneLineRepository = phoneLineRepository;
        this.cityService = cityService;
    }

    public List<Client> getAll(Integer quantity, Integer from) {
        return this.clientRepository.findAll(quantity,from);
    }

    public Client create(ClientCreatedDTO client) throws CityNotFoundException {
        Client clientCreated = Client.builder()
                .firstname(client.getFirstname())
                .surname(client.getSurname())
                .city(this.cityService.getByName(client.getCityName()))
                .DNI(client.getDni())
                .username(client.getUsername())
                //TODO hashear password
                .password(client.getPassword())
                .build();
        return this.clientRepository.save(clientCreated);
    }


    public PhoneLine setPhoneline(PhoneLine phoneLine) {
        return phoneLineRepository.save(phoneLine);
    }

    public Client getById(Integer id) throws ClientNotFoundException {
        return this.clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException());
    }

    public Client update(Integer idclient, ClientUpdatedDTO modifiedClient) throws ClientNotFoundException, CityNotFoundException {
        Client outdatedClient = clientRepository.findById(idclient)
                .orElseThrow(ClientNotFoundException::new);

        Client updated = updateClient(outdatedClient, modifiedClient);
        return clientRepository.save(updated);
    }

    private Client updateClient(Client oldClient, ClientUpdatedDTO modifiedClient) throws CityNotFoundException {
        oldClient.setFirstname(modifiedClient.getFirstname());
        oldClient.setSurname(modifiedClient.getSurname());
        oldClient.setCity(this.cityService.getByName(modifiedClient.getCityName()));
        oldClient.setPassword(modifiedClient.getPassword());
        oldClient.setUsername(modifiedClient.getUsername());
        return oldClient;
    }

    public Integer delete(Integer idClient) throws ClientNotFoundException, ClientIsAlreadyDeletedException {
        Client clientToBeDeleted = getById(idClient);
        if (!clientToBeDeleted.getIsActive()) {
            throw new ClientIsAlreadyDeletedException();
        }
        return clientRepository.deleteClient(clientToBeDeleted.getId());
    }
}
