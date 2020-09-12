package com.utnphones.utnPhones.repository;


import com.utnphones.utnPhones.domain.Client;
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
}

