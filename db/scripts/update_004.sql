create or replace view  userview  as
(select u.id,  u.name, u.mail, u.pass, co.country, ci.city, r.roles from users as u
left outer join adreshelp as ad on ad.user_id = u.id
left outer join country as co on co.id = ad.country_id
left outer join city as ci on ci.id = ad.city_id
left outer join rolesusers as ru on ru.users_id = u.id
left outer join roles as r on r.id = ru.roles_id
);