package com.utnphones.utnPhones.repository;


import com.utnphones.utnPhones.domain.LineType;
import com.utnphones.utnPhones.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineTypeRepository extends JpaRepository<LineType,Integer> {
}
