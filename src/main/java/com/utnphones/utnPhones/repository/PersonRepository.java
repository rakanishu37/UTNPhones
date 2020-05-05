package com.utnphones.utnPhones.repository;

import com.utnphones.utnPhones.domain.Person;
import com.utnphones.utnPhones.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {
}
