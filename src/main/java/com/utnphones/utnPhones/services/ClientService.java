package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.dto.ClientCreatedDTO;
import com.utnphones.utnPhones.dto.ClientUpdatedDTO;
import com.utnphones.utnPhones.exceptions.CityNotFoundException;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.repository.ClientRepository;
import com.utnphones.utnPhones.utils.PasswordConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class ClientService {
    private ClientRepository clientRepository;
    private CityService cityService;

    @Autowired
    public ClientService(ClientRepository clientRepository, CityService cityService) {
        this.clientRepository = clientRepository;
        this.cityService = cityService;
    }

    public List<Client> getAll(Integer quantity, Integer from) {
        return this.clientRepository.findAll(quantity,from);
    }

    public Client create(ClientCreatedDTO client) throws CityNotFoundException, NoSuchAlgorithmException {
        String password = PasswordConverter.generatePassword(client.getPassword());

        Client clientCreated = Client.builder()
                .firstname(client.getFirstname())
                .surname(client.getSurname())
                .city(cityService.getByName(client.getCityName()))
                .DNI(client.getDni())
                .username(client.getUsername())
                .password(password)
                .build();

        return this.clientRepository.save(clientCreated);
    }

    public Client getById(Integer id) throws ClientNotFoundException {
        return this.clientRepository.findByIdAndIsActive(id,Boolean.TRUE)
                .orElseThrow(ClientNotFoundException::new);
    }

    public Client update(Integer idclient, ClientUpdatedDTO modifiedClient) throws ClientNotFoundException, CityNotFoundException, NoSuchAlgorithmException {
        Client outdatedClient = clientRepository.findByIdAndIsActive(idclient,Boolean.TRUE)
                .orElseThrow(ClientNotFoundException::new);

        Client updated = updateClient(outdatedClient, modifiedClient);
        return clientRepository.save(updated);
    }

    private Client updateClient(Client oldClient, ClientUpdatedDTO modifiedClient) throws CityNotFoundException, NoSuchAlgorithmException {
        oldClient.setFirstname(modifiedClient.getFirstname());
        oldClient.setSurname(modifiedClient.getSurname());
        oldClient.setCity(this.cityService.getByName(modifiedClient.getCityName()));
        oldClient.setPassword(PasswordConverter.generatePassword(modifiedClient.getPassword()));
        oldClient.setUsername(modifiedClient.getUsername());
        return oldClient;
    }

    public void delete(Integer idClient) throws ClientNotFoundException {
        Client clientToBeDeleted = getById(idClient);
        clientToBeDeleted.setIsActive(Boolean.FALSE);
        clientRepository.save(clientToBeDeleted);
    }
}
