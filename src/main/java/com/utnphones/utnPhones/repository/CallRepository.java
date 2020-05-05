package com.utnphones.utnPhones.repository;

import com.utnphones.utnPhones.domain.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallRepository extends JpaRepository<Call,Integer> {
}
