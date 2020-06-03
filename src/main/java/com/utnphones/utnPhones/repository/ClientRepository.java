package com.utnphones.utnPhones.repository;


import com.utnphones.utnPhones.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {
    Page<Client> findAll(Pageable pageable);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update persons set is_active = false where id_person = ?1", nativeQuery = true)
    Integer deleteClient(Integer id_person);
}
