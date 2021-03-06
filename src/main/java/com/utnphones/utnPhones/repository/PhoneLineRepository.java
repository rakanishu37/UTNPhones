package com.utnphones.utnPhones.repository;

import com.utnphones.utnPhones.domain.LineStatus;
import com.utnphones.utnPhones.domain.PhoneLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhoneLineRepository extends JpaRepository<PhoneLine,Integer> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update phone_lines set line_status = ?1 where id_phone_line = ?2", nativeQuery = true)
    Integer changePhoneLineStatus(LineStatus status, Integer idPhoneLine);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update phone_lines set is_active = false where id_phone_line = ?1", nativeQuery = true)
    Integer deletePhoneLine(Integer idPhoneline);

    Optional<PhoneLine> findByLineNumberAndIsActive(String phoneNumber,Boolean b);

    Optional<PhoneLine> findByLineNumberAndIsActiveAndLineStatus(String phoneNumber,Boolean b, LineStatus active);

    List<PhoneLine> findAllByIsActive(Boolean b);

    Optional<PhoneLine> findByIdAndIsActive(Integer id, Boolean aTrue);
}
