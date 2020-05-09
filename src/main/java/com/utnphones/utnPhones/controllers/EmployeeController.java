package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Employee;
import com.utnphones.utnPhones.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public List<Employee> getAll(){
        return this.employeeService.getAll();
    }
}
