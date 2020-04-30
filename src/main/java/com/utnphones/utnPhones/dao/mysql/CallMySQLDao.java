package com.utnphones.utnPhones.dao.mysql;

import com.utnphones.utnPhones.dao.CallDao;
import com.utnphones.utnPhones.domain.Call;
import lombok.Data;

import java.sql.Connection;
import java.util.List;

@Data
public class CallMySQLDao implements CallDao {
    private Connection connection;

    @Override
    public Call add(Call value) {
        return null;
    }

    @Override
    public Call update(Call value) {
        return null;
    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public void remove(Call value) {

    }

    @Override
    public Call getById(Integer id) {
        return null;
    }

    @Override
    public List<Call> getAll() {
        return null;
    }
}
