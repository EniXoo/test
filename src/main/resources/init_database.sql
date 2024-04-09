drop table if exists asset cascade ;
create table if not exists asset(
    code varchar(255) primary key,
    name varchar(255) not null,
    activation_cost numeric not null,
    volume integer not null
);

drop table if exists day cascade;
create table day(
    id serial primary key,
    name varchar(10) not null
);

drop table if exists availability cascade;
create table availability(
    asset_code varchar(255) not null references asset,
    day_id serial not null references day,
    primary key (asset_code, day_id)
);

drop table if exists tso_request cascade;
create table tso_request
(
    id serial primary key,
    activation_date date not null,
    requested_at timestamp default CURRENT_TIMESTAMP not null,
    requested_by varchar(255) not null,
    volume integer not null,
    cost numeric
);

drop table if exists tso_response cascade;
create table tso_response
(
    id serial primary key,
    tso_id serial references tso_request,
    asset_code varchar(255) not null references asset,
    volume integer not null,
    cost numeric not null
);

insert into asset(code, name, activation_cost, volume)
values ('A1', 'Asset 1', 100.00, 60),
       ('A2', 'Asset 2', 110.00, 60);

insert into day(name)
values ('MONDAY'),
       ('TUESDAY'),
       ('WEDNESDAY');

insert into availability(asset_code, day_id)
values ('A1', 1),
       ('A1', 2),
       ('A2', 1),
       ('A2', 3);
