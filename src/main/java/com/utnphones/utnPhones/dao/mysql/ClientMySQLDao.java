package com.utnphones.utnPhones.dao.mysql;

import com.utnphones.utnPhones.dao.ClientDao;
import com.utnphones.utnPhones.domain.Client;
import lombok.Data;

import java.sql.Connection;
import java.util.List;

@Data
public class ClientMySQLDao implements ClientDao {
    private Connection connection;

    @Override
    public Client add(Client value) {
        return null;
    }

    @Override
    public Client update(Client value) {
        return null;
    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public void remove(Client value) {

    }

    @Override
    public Client getById(Integer id) {
        return null;
    }

    @Override
    public List<Client> getAll() {
        return null;
    }
}
