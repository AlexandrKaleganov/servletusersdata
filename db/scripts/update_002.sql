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
