create table if not exists  roles(
id serial primary key,
roles varchar(200)
);
insert into roles(roles) values('Admin'),('User');

create table if not exists  rolesusers(
  id_users integer references users(id) ,
  id_roles integer references roles(id),
  PRIMARY KEY (id_users, id_roles)
);