package com.utnphones.utnPhones.repository;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.projections.DestinyQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CallRepository extends JpaRepository<Call,Integer> {

    @Query(value = "call sp_show_report_by_idClient_dates(:idClient,:dateFrom,:dateTo) LIMIT :quantity OFFSET :from ;", nativeQuery = true)
    List<CallsDates> getAllByIdClientBetweenDates(@Param("idClient") Integer idClient, String dateFrom, String dateTo, @Param("from") Integer from, @Param("quantity") Integer quantity);

    @Query(value = "select * from v_report LIMIT :quantity OFFSET :from ", nativeQuery = true)
    List<CallsDates> findAll(@Param("quantity") Integer quantity, @Param("from") Integer from);

    @Query(value = "select * from v_report WHERE v_report.date BETWEEN :datefrom and :dateTo LIMIT :quantity OFFSET :from ", nativeQuery = true)
    List<CallsDates> findAllByDates(@Param("from") Integer from, @Param("quantity") Integer quantity,
                                    @Param("datefrom") String dateFrom, @Param("dateTo") String dateTo);


    @Query(value = "call sp_show_report_by_idClient(:id_person) LIMIT :quantity OFFSET :from ;", nativeQuery = true)
    List<CallsDates> getAllCallByClient(@Param("id_person") Integer id, @Param("from") Integer from, @Param("quantity") Integer quantity);

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
    List<DestinyQuantity> getTopTenDestiniesByClient(@Param("idClient") Integer idClient);
}
