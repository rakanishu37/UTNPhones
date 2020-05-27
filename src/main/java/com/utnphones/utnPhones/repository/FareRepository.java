package com.utnphones.utnPhones.repository;

import com.utnphones.utnPhones.domain.Fare;
import com.utnphones.utnPhones.domain.Province;
import com.utnphones.utnPhones.projections.FareByCities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FareRepository extends JpaRepository<Fare,Integer> {
    @Query(value = "select" +
                   " fares.price" +
                   " from fares " +
                   " where fares.id_city_from = :id_city_from and fares.id_city_to = :id_city_to", nativeQuery = true)
    FareByCities getFareByCities(@Param("id_city_from") Integer idCityFrom, @Param("id_city_to") Integer idCityTo);
}
