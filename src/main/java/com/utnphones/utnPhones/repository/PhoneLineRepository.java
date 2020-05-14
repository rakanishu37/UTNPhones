package com.utnphones.utnPhones.repository;

import com.utnphones.utnPhones.domain.PhoneLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PhoneLineRepository extends JpaRepository<PhoneLine,Integer> {

    //Todo un get que traiga las activas o con un parametro opcional para discriminar por el estado

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update phone_lines set line_status = 'canceled' where id_phone_line = ?1", nativeQuery = true)
    Integer deletePhoneLine(Integer id_phone_line);

    //todo en duda v
/*    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update phone_lines set line_status = 'suspended' where id_phone_line = ?1", nativeQuery = true)
    Integer suspendPhoneLine(Integer id_phone_line);*/
}
