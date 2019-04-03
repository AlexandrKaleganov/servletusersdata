--основная база услуг наименование, количество и цена за еденицу
--insert into service(name, maxcount, price) values ('картридж', 90, 375.50);
create table if not exists  service(
id serial primary key,
name varchar(200),
maxcount integer,
price money,
date timestamp not null default now()
);
--insert into service(name, maxcount, price) values ('картридж', 90, 375.50);
--insert into service(name, maxcount, price) values ('картридж2', 90, 375.50);
--лист, который я буду получать, лист  всех услуг с ценой
create or replace view  summary as (
select id, name, maxcount, price, (maxcount * price) as summ , date from service
);
-- тут будут ханится счета, которые нам будут отправлять на утверждение после заправки картриджей(после оказанных услу)
--если всё верно и нас устраивает  мы будем жать утвердить и данные будут применены в другую таблицу а эта таблица будет очищаться
--команда очистки: delete from service_temp where numberschet = 123;  удалять буду по счёту в случае применения
--insert into service_temp (numberschet, id_service, maxcount) values(123, 1, 8);
create table if not exists  service_temp(
numberschet integer,
id_service integer references  service(id),
maxcount integer,
date timestamp not null default now()
);
--insert into service_temp (numberschet, id_service, maxcount) values(123, 1, 8);
-- сюда будем добавлять одобренные услуги(утверждённые счета)
--insert into service_res (numberschet, id_service, maxcount, date) values(123, 1, 8, '03-04-2019');
create table if not exists  service_res(
numberschet integer,
id_service integer references  service(id),
maxcount integer,
date timestamp
);
--insert into service_res (numberschet, id_service, maxcount, date) values(123, 1, 8, '03-04-2019');
--моя вью таблица отслеживающая остатки на дату
create or replace view service_ostatki as (select
s.id, s.name, (s.maxcount - COALESCE(res.maxcount, 0)) as ost_count, s.price,
((s.maxcount - COALESCE(res.maxcount, 0)) * s.price) as ost_money, COALESCE(res.date, s.date) as date
from service as s left outer join service_res as res on s.id = res.id_service);
--выборка на дату сколько осталось услуг
--select * from service_ostatki where date BETWEEN '01-01-1990' AND '03-04-2019';
--если отправлена заявка то выборка покажет сколько останется остатков после применения
--select id, name, (o.ost_count - COALESCE(temp.maxcount, 0)) as temp_ost_count, ((o.ost_count - COALESCE(temp.maxcount, 0)) * price)
--as temp_ost_money, coalesce(temp.date, o.date) from service_ostatki as o left outer join
--service_temp as temp on o.id = temp.id_service;