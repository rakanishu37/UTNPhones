package com.utnphones.utnPhones.repository;

import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City,Integer> {
    Optional<City> findByName(String cityName);
}
