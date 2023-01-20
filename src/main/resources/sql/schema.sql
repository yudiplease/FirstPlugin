drop table if exists account;

create table account (
    id uuid primary key,
    username varchar(20),
    hash_password varchar(250)
);