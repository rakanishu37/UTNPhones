package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.exceptions.ClientIsAlreadyDeletedException;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.repository.CallRepository;
import com.utnphones.utnPhones.repository.ClientRepository;
import com.utnphones.utnPhones.repository.PhoneLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ClientService(final ClientRepository clientRepository,PhoneLineRepository phoneLineRepository,CallRepository callRepository) {
        this.clientRepository = clientRepository;
        this.phoneLineRepository = phoneLineRepository;
        this.callRepository = callRepository;
    }

    public List<Client> getAll(){
        return this.clientRepository.findAll();
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

    public Client update(Client client) throws ClientNotFoundException {
		return clientRepository.findById(client.getId())
            .map(clientRepository::save)
            .orElseThrow(() -> new ClientNotFoundException());
    }

    public Integer delete(Integer idClient) throws ClientNotFoundException, ClientIsAlreadyDeletedException {
        Client clientToBeDeleted = getById(idClient);
        if(!clientToBeDeleted.getIsActive()){
            throw new ClientIsAlreadyDeletedException();
        }
        return clientRepository.deleteClient(clientToBeDeleted.getId());
    }

    public Map<String, List<CallsDates>> getCallsBetweenDates(Integer idClient, Date from, Date to){
        List<CallsDates> calls = callRepository.findByDateBetween(idClient,from,to);

        return calls.stream()
                .sorted(Comparator.comparing(CallsDates::getDate))
                .collect(Collectors.groupingBy(CallsDates::getOrigin));
    }
}
