package com.utnphones.utnPhones.repository;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.projections.TopTenDestinies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CallRepository extends JpaRepository<Call,Integer> {

    /*@Query(value = "select " +
            "            plFrom.line_number as 'origin', " +
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
    List<CallsDates> findByDateBetween(@Param("clientId")Integer clientId,@Param("from") Date from,@Param("to") Date to);*/
    @Query(value = "call sp_show_report_by_idClient_dates(:idClient,:from,:to);", nativeQuery = true)
    List<CallsDates> getAllByIdClientBetweenDates(@Param("idClient") Integer idClient,Date from, Date to);

    /*@Transactional
    @Modifying(clearAutomatically = true)
    @Query(value ="insert into calls(id_phone_line_from, id_phone_line_to, duration, date_call) values (?1,?2,?3,?4)" ,nativeQuery =   true)
    Integer saveCall(Integer numberFrom,Integer numberTo,Integer duration,Date dateCall);*/

    @Query(value = "select * from v_report LIMIT :quantity OFFSET :from ", nativeQuery = true)
    List<CallsDates> findAll(@Param("quantity") Integer quantity, @Param("from") Integer from);

    @Query(value = "select * from v_report WHERE v_report.date BETWEEN :datefrom and :dateTo LIMIT :quantity OFFSET :from ", nativeQuery = true)
    List<CallsDates> findAllByDates(@Param("from") Integer from, @Param("quantity") Integer quantity,
                                    @Param("datefrom") String dateFrom, @Param("dateTo") String dateTo);

    @Query(value = "call sp_show_report_by_idClient(:id_person);", nativeQuery = true)
    List<CallsDates> getAllCallByClient(@Param("id_person") Integer id);

    @Query(value = "select \n" +
            "\tct.city_name as 'CityName',\n" +
            "\tcount(pl.line_number) as 'NumberOfCalls'\n" +
            "from \n" +
            "\tcalls as c\n" +
            "    inner join phone_lines as pl on c.id_phone_line_to = pl.id_phone_line\n" +
            "    inner join cities as ct on ct.id_city = (select id_city from cities where pl.line_number like CONCAT(prefix,'%') order by LENGTH(prefix) DESC LIMIT 1)\n" +
            "\t\n" +
            "    where \n" +
            "\t\tc.id_phone_line_from in (select  id_phone_line from phone_lines where id_person = :idClient)\n" +
            "group by\n" +
            "\tct.city_name\n" +
            "order by count(pl.line_number)\n" +
            "limit \n" +
            "10;", nativeQuery = true)
    List<TopTenDestinies> getTopTenDestiniesByClient(@Param("idClient") Integer idClient);
}
