package com.utnphones.utnPhones.repository;

import com.utnphones.utnPhones.domain.Invoice;
import com.utnphones.utnPhones.projections.InvoiceByClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
    List<InvoiceByClient> getByIdClientDateBetween(@Param("clientId")Integer clientId,
                                                   @Param("from") String from, @Param("to") String to);


    @Query(value = "select " +
            "         inv.id_invoice as 'idInvoice', " +
            "         pl.line_number as 'PhoneLineNumber'," +
            "         inv.number_of_calls as 'NumberOfCalls'," +
            "         inv.price_cost as 'PriceCost', " +
            "         inv.invoice_date as 'InvoiceDate'," +
            "         inv.due_date as 'DueDate'," +
            "         inv.total_price as 'TotalPrice', " +
            "         inv.paid as 'Paid', " +
            "         per.firstname as 'FirstName', " +
            "         per.surname as 'LastName' " +
            "       from " +
            "           invoices as inv\n" +
            "           inner join phone_lines as pl on inv.id_line = pl.id_phone_line\n" +
            "           inner join persons as per on pl.id_person = per.id_person \n" +
            "       where " +
            "           per.id_person = :clientId " +
            "       order by " +
            "           inv.invoice_date asc;", nativeQuery = true)
    List<InvoiceByClient> getInvoicesByClient(@Param("clientId") Integer clientId);

    @Query(value = "select inv.id_invoice as 'idInvoice', pl.line_number as 'PhoneLineNumber',\n" +
            "            inv.number_of_calls as 'NumberOfCalls', inv.price_cost as 'PriceCost', inv.invoice_date as 'InvoiceDate',\n" +
            "            inv.due_date as 'DueDate', inv.total_price as 'TotalPrice', inv.paid as 'Paid' \n" +
            "            , per.firstname as 'FirstName', per.surname as 'LastName' \n" +
            "            from invoices as inv\n" +
            "            inner join phone_lines as pl on inv.id_line = pl.id_phone_line\n" +
            "            inner join persons as per on pl.id_person = per.id_person \n" +
            "             where per.id_person = :clientId and inv.invoice_date between :dateFrom and :dateTo \n" +
            "            order by inv.invoice_date asc;", nativeQuery = true)
    List<InvoiceByClient> getInvoicesByClientBetweenDates(@Param("clientId") Integer clientId,
                                                          @Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo);
}
