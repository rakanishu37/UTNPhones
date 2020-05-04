package com.utnphones.utnPhones.dao.mysql;

import com.utnphones.utnPhones.dao.interfaces.InvoiceDao;
import com.utnphones.utnPhones.domain.Invoice;
import lombok.Data;

import java.sql.Connection;
import java.util.List;

@Data
public class InvoiceMySQLDao implements InvoiceDao {
    private Connection connection;

    @Override
    public Invoice add(Invoice value) {
        return null;
    }

    @Override
    public Invoice update(Invoice value) {
        return null;
    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public void remove(Invoice value) {

    }

    @Override
    public Invoice getById(Integer id) {
        return null;
    }

    @Override
    public List<Invoice> getAll() {
        return null;
    }
}
