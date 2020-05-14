package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.ClientMySQLDao;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private ClientMySQLDao clientMySQLDao;
    private ClientRepository clientRepository;

    public ClientService(final ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /*public List<Client> getAll(){
        return this.clientMySQLDao.getAll();
    }*/
    public List<Client> getAll(){
        return this.clientRepository.findAll();
    }

    public Client create(Client client){
        return this.clientRepository.save(client);
    }

    public Optional<Client> getById(Integer id){
        return this.clientRepository.findById(id);
    }
}