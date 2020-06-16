package com.utnphones.utnPhones.repository;


import com.utnphones.utnPhones.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update persons set is_active = false where id_person = ?1", nativeQuery = true)
    Integer deleteClient(Integer id_person);

    @Query(value = "select * from persons where id_user_type = 1 and is_active = true LIMIT :quantity OFFSET :from ", nativeQuery = true)
    List<Client> findAll(@Param("quantity") Integer quantity, @Param("from") Integer from);
}

