package com.utnphones.utnPhones.repository;

import com.utnphones.utnPhones.domain.Employee;
import com.utnphones.utnPhones.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}
