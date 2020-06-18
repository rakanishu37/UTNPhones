use utn_phones;

explain  select * from calls;
select count(*) from calls;
-- select count * sin index 0.235
-- select * from calls sin index 0.391, con index en todas las columnas tarda lo mismo
select count(*) from phone_lines;

explain select calls.duration from calls order by calls.duration asc; -- 0.281 sin index, lo mismo con index 
-- si hay una pk o fk va a usar ese index, en duration usa el index creado por mi
drop index idx_calls on calls;
create index idx_calls on calls(duration) using btree;

select count(*) from calls where date_call between '2020-05-10' and '2020-05-17'; --  0.219 no index, index 0.156
create index idx_calls_date on calls(date_call) using btree;


SELECT table_name AS "Table",
ROUND(((data_length + index_length) / 1024 / 1024), 2) AS "Size (MB)"
FROM information_schema.TABLES
WHERE table_schema = "utn_phones"
ORDER BY (data_length + index_length) DESC;


select plFrom.line_number as 'origin', 
                        plTo.line_number as 'destiny',
                        c.fare as 'fare',
                        c.duration as 'duration',
                        c.total_price as 'TotalPrice',
                        c.date_call as 'date'
                    from
                        calls as c
                        inner join phone_lines as plFrom on c.id_phone_line_from = plFrom.id_phone_line
                        inner join phone_lines as plTo on c.id_phone_line_to = plTo.id_phone_line
                    where
                        c.id_phone_line_from in (select id_phone_line from phone_lines where id_person = 1) and
                        c.date_call between '2000-01-01' and '2021-01-01'
                    order by
                        c.id_phone_line_from desc;
