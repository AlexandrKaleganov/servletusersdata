--основная база услугЖ наименование, количество и цена за еденицу
--insert into service(name, maxcount, price) values ('картридж', 90, 375.50);
create table if not exists  service(
id serial primary key,
name varchar(200),
maxcount integer,
price money,
date timestamp not null default now()
);
--лист, который я буду получать, лист  всех услуг
create or replace view  summary as (
select id, name, maxcount, price, (maxcount * price) as summ , date from service
);
-- тут будут ханится счета, которые нам будут отправлять на утверждение после заправки картриджей(после оказанных услу)
--если всё верно и нас устраивает  мы будем жать утвердить и данные будут применены в другую таблицу а эта таблица будет очищаться
--insert into service_temp (numberschet, id_service, maxcount) values(123, 1, 8);
create table if not exists  service_temp(
numberschet integer,
id_service integer references  service(id),
maxcount integer,
date timestamp not null default now()
);
-- сюда будем добавлять одобренные услуги(утверждённые счета)
--insert into service_res (numberschet, id_service, maxcount, date) values(123, 1, 8, '03-04-2019');
create table if not exists  service_res(
numberschet integer,
id_service integer references  service(id),
maxcount integer,
date timestamp
);
