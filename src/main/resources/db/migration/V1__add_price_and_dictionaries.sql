create table category (
    category_id int not null primary key,
    name varchar(255) not null unique
);

create table item (
    item_id int not null primary key,
    name varchar(255) not null unique,
    category_id int references category (category_id)
);

create table city (
    city_id int not null primary key,
    name varchar(255) not null unique,
    country varchar(255) not null
);

create table location (
    location_id int not null primary key,
    name varchar(255) not null unique,
    city_id int not null references city (city_id)
);

create table price (
    id int not null primary key,
    item_id int not null references item (item_id),
    amount int not null,
    date date not null,
    location_id int references location (location_id),
    create_date timestamp not null,
    update_date timestamp not null
);

create sequence price_seq;
