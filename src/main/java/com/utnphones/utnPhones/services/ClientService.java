package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.ClientMySQLDao;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class    ClientService {
    private ClientRepository clientRepository;

    public ClientService(final ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAll(){
        return this.clientRepository.findAll();
    }

    public Client create(Client client){
        return this.clientRepository.save(client);
    }

    public Client getById(Integer id) throws ClientNotFoundException {
        return this.clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("client not found"));
    }

    public Client update(Client client) throws ClientNotFoundException {
		return clientRepository.findById(client.getId())
            .map(clientRepository::save)
            .orElseThrow(() -> new ClientNotFoundException("client not found"));
    }

    public Integer delete(Client client) {
        return this.clientRepository.deleteClient(client.getId());
    }
}
