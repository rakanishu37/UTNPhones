package com.utnphones.utnPhones.repository;

import com.utnphones.utnPhones.domain.Person;
import com.utnphones.utnPhones.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {
    @Query(value = "select * from persons where persons.username = :username and persons.password = :password", nativeQuery = true)
    Person getByUsername(@Param("username") String username, @Param("password") String password);
}
