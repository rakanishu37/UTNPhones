package com.utnphones.utnPhones.dao.mysql;

import com.utnphones.utnPhones.dao.EmployeeDao;
import com.utnphones.utnPhones.domain.Employee;
import lombok.Data;

import java.sql.Connection;
import java.util.List;

@Data
public class EmployeeMySQLDao implements EmployeeDao {
    private Connection connection;

    @Override
    public Employee add(Employee value) {
        return null;
    }

    @Override
    public Employee update(Employee value) {
        return null;
    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public void remove(Employee value) {

    }

    @Override
    public Employee getById(Integer id) {
        return null;
    }

    @Override
    public List<Employee> getAll() {
        return null;
    }
}
