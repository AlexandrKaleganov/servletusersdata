--insert into users(name, login, pass) values('name', 'login', 'pass');
--insert into country(country) values ('Россия');
--insert into city(city) values ('Москва');
--insert into accesAttrib(accesAttrib) values ('Админ');
--insert into adreshelp(user_id, country_id, city_id) values (2, 3, 3);
--insert into accesAttribhelp(user_id, accesAttrib_id) values(1,3);

create or replace view  userview  as select u.id, u.create_date, u.name, u.login, u.pass,  at.accesAttrib, co.country, ci.city from users as u inner join adreshelp as ad on ad.user_id = u.id
inner join country as co on co.id = ad.country_id
inner join city as ci on ci.id = ad.city_id
inner join accesAtribhelp as ath on ath.user_id = u.id
inner join accesAttrib as at on ath.accesAttrib_id = at.id;
select * from userviev;

select * from adreshelp