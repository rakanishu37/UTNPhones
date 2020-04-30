package com.utnphones.utnPhones.dao.mysql;

import com.utnphones.utnPhones.dao.PhoneLineDao;
import com.utnphones.utnPhones.domain.PhoneLine;
import lombok.Data;

import java.sql.Connection;
import java.util.List;

@Data
public class PhoneLineMySQLDao implements PhoneLineDao {
    private Connection connection;

    @Override
    public PhoneLine add(PhoneLine value) {
        return null;
    }

    @Override
    public PhoneLine update(PhoneLine value) {
        return null;
    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public void remove(PhoneLine value) {

    }

    @Override
    public PhoneLine getById(Integer id) {
        return null;
    }

    @Override
    public List<PhoneLine> getAll() {
        return null;
    }
}
