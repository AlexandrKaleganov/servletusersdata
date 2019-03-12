create table if not exists  roles(
id serial primary key,
roles varchar(200)
);
insert into roles(roles) values('ADMIN'),('USER');

create table if not exists  rolesusers(
  id_users integer references users(id) primary key,
  id_roles integer references roles(id)
);
create or replace view  userview  as
(select u.id,  u.name, u.mail, u.pass, co.country, ci.city, r.roles from users as u
inner join adreshelp as ad on ad.user_id = u.id
inner join country as co on co.id = ad.country_id
inner join city as ci on ci.id = ad.city_id
inner join roles as r on r.id = (select rolesusers.id_users from rolesusers where rolesusers.id_users = u.id)
);