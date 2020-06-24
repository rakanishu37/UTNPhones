package com.utnphones.utnPhones.repository;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.dto.CallDto;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.projections.PersonDuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface CallRepository extends JpaRepository<Call,Integer> {

    @Query(value = "select\n" +
            "            plFrom.line_number as 'origin',\n" +
            "            plTo.line_number as 'destiny',\n" +
            "            c.fare as 'fare',\n" +
            "            c.duration as 'duration',\n" +
            "            c.total_price as 'TotalPrice',\n" +
            "            c.date_call as 'date'\n" +
            "        from\n" +
            "            calls as c\n" +
            "            inner join phone_lines as plFrom on c.id_phone_line_from = plFrom.id_phone_line\n" +
            "            inner join phone_lines as plTo on c.id_phone_line_to = plTo.id_phone_line\n" +
            "        where\n" +
            "            c.id_phone_line_from in (select id_phone_line from phone_lines where id_person = :clientId) and\n" +
            "            c.date_call between :from and :to\n" +
            "        order by\n" +
            "            c.id_phone_line_from desc;", nativeQuery = true)
    List<CallsDates> findByDateBetween(@Param("clientId")Integer clientId,@Param("from") Date from,@Param("to") Date to);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value ="insert into calls(id_phone_line_from, id_phone_line_to, duration, date_call) values (?1,?2,?3,?4)" ,nativeQuery =   true)
    Integer saveCall(Integer numberFrom,Integer numberTo,Integer duration,Date dateCall);


    @Query(value = "select  " +
            "   p.firstname as 'name', " +
            "   p.surname as 'lastname', " +
            "   sum(c.duration) as 'totalDuration' " +
            "from " +
            "    calls as c " +
            "    inner join phone_lines as pl on c.id_phone_line_from = pl.id_phone_line " +
            "    inner join persons as p on p.id_person = pl.id_person " +
            "where  " +
            "   c.date_call like CONCAT(:month,'%') " +
            "group by " +
            "   p.id_person", nativeQuery = true)
    List<PersonDuration> getDurationInMonth(@Param("month") String month);
}
