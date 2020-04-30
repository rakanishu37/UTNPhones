delimiter //
create procedure sp_add_phone_line(IN pLineType VARCHAR(50), IN pUsername VARCHAR(50), IN pLineNumber VARCHAR(15), IN pLineStatus ENUM('active','canceled','suspended'), IN CityName varchar(50))
begin 
	declare vPersonId int default 0;
    declare vLineTypeId int;
	declare vPhoneNumber varchar(15);
    select id_person into vPersonId from persons as p where p.username = pUsername;
    
    if(vPersonId = 0) then
		signal sqlstate '10001' SET MESSAGE_TEXT = 'Client does not exists', MYSQL_ERRNO = 3000 ;
	else
		select id_line_type into vLineTypeId from line_types as lt where lt.type_name = pLineType;
        set vPhoneNumber = (select (c.prefix,pLineNumber) from cities as c where c.city_name= CityName);
        
		insert into phone_lines(id_line_type,id_person,line_number,line_status) values(vLineTypeId,vPersonId, vPhoneNumber, pLineStatus);
	end if;
end; //
