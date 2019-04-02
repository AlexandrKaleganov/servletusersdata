create table if not exists  service(
id serial primary key,
name varchar(200),
maxcount integer,
price money,
date timestamp not null default now()
);
create or replace view  summary as (
select id, name, maxcount, price, (maxcount * price), date as summ from service
);


--insert into service(name, maxcount, price) values ('картридж', 90, 375.50);