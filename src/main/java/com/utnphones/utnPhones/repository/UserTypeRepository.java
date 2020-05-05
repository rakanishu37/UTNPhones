package com.utnphones.utnPhones.repository;

import com.utnphones.utnPhones.domain.Province;
import com.utnphones.utnPhones.domain.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType,Integer> {
}
