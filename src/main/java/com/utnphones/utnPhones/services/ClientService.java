package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.ClientMySQLDao;
import com.utnphones.utnPhones.domain.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@AllArgsConstructor
public class ClientService {
    private ClientMySQLDao clientMySQLDao;

    public List<Client> getAll(){
        return this.clientMySQLDao.getAll();
    }
}
