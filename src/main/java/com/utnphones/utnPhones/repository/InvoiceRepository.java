package com.utnphones.utnPhones.repository;

import com.utnphones.utnPhones.domain.Invoice;
import com.utnphones.utnPhones.domain.Province;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.projections.InvoicesDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Integer> {

    @Query(value = "select\n" +
            "            pl.line_number as 'PhoneLine',\n" +
            "            inv.number_of_calls as 'NumberOfCalls',\n" +
            "            inv.price_cost as 'PriceCost',\n" +
            "            inv.total_price as 'TotalPrice',\n" +
            "            inv.invoice_date as 'InvoiceDate',\n" +
            "            inv.due_date as 'DueDate',\n" +
            "        from\n" +
            "            invoices as inv\n" +
            "            inner join phone_lines as pl on inv.id_line = pl.id_phone_line\n" +
            "        where\n" +
            "            pl.id_person = :clientId and\n" +
            "            inv.invoice_date between :from and :to\n" +
            "        order by\n" +
            "            inv.invoice_date asc;", nativeQuery = true)
    List<InvoicesDates> getByIdClientDateBetween(@Param("clientId")Integer clientId, @Param("from") Date from, @Param("to") Date to);
}
