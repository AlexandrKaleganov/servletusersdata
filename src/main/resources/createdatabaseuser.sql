create table if not exists  users(
id serial primary key,
name varchar(200),
mail varchar(200),
pass varchar(200)
);
create table if not exists country(
id serial primary key,
country varchar(200)
);
create table if not exists  city(
id serial primary key,
city varchar(200),
country_id integer references country(id)
);
create table if not exists adreshelp(
user_id integer references users(id) primary key,
country_id integer references country(id),
city_id integer references city(id)
);
create or replace view  userview  as 
(select u.id,  u.name, u.mail, u.pass, co.country, ci.city from users as u
inner join adreshelp as ad on ad.user_id = u.id
inner join country as co on co.id = ad.country_id
inner join city as ci on ci.id = ad.city_id);