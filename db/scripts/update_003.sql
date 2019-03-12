create table if not exists  roles(
id serial primary key,
roles varchar(200)
);

insert into roles(roles) values('ADMIN'),('USER');

create table if not exists  rolesusers(
  users_id integer references users(id) primary key,
  roles_id integer references roles(id)
);
create or replace view  userview  as
(select u.id,  u.name, u.mail, u.pass, co.country, ci.city, r.roles from users as u
inner join adreshelp as ad on ad.user_id = u.id
inner join country as co on co.id = ad.country_id
inner join city as ci on ci.id = ad.city_id
inner join rolesusers as ru on ru.users_id = u.id
inner join roles as r on r.id = ru.roles_id
);