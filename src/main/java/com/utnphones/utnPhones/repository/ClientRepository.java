package com.utnphones.utnPhones.repository;


import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.dto.TotalPriceDTO;
import com.utnphones.utnPhones.projections.TotalPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {
    @Query(value = "select * from persons where id_user_type = 1 and is_active = true LIMIT :quantity OFFSET :from ", nativeQuery = true)
    List<Client> findAll(@Param("quantity") Integer quantity, @Param("from") Integer from);

    Optional<Client> findByIdAndIsActive(Integer id, Boolean aTrue);

    @Query(value = "select persons.firstname as 'firstname', " +
            "persons.surname as 'surname', " +
            "sum(calls.total_price) as 'totalPrice' " +
            "from calls\n" +
            "inner join phone_lines on phone_lines.id_phone_line = calls.id_phone_line_from\n" +
            "inner join persons on persons.id_person = :idClient \n" +
            "group by(persons.id_person);", nativeQuery = true)
    TotalPrice getTotalPrice(@Param("idClient") Integer idClient);
}

