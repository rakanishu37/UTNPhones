package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    //private EmployeeMySQLDao employeeMySQLDao;
    private EmployeeRepository employeeRepository;
   /* @Autowired
    public EmployeeService(final EmployeeMySQLDao employeeMySQLDao) {
        this.employeeMySQLDao = employeeMySQLDao;
    }*/
    @Autowired
    public EmployeeService(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
}
