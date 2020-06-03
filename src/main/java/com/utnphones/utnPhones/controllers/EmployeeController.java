package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Employee;
import com.utnphones.utnPhones.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

   // @GetMapping("/")
    public List<Employee> getAll(){
        return this.employeeService.getAll();
    }

   // @PostMapping("/")
    public Employee create(@RequestBody Employee employee){
        return this.employeeService.create(employee);
    }
}
