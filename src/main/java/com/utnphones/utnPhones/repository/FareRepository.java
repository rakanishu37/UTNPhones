package com.utnphones.utnPhones.repository;

import com.utnphones.utnPhones.domain.Fare;
import com.utnphones.utnPhones.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FareRepository extends JpaRepository<Fare,Integer> {
}
