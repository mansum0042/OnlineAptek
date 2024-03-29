-- create table users
-- (
--     id         serial primary key,
--     name       varchar(100) not null,
--     surname    varchar(100) not null,
--     email      varchar(255) not null,
--     password   varchar(255) not null,
--     birthday   date,
--     created_at date,
--     role       varchar(50)
-- );
--
-- create table branches
-- (
--     id          serial primary key,
--     name        varchar(100) not null,
--     description varchar(100) not null,
--     user_id     int,
--     constraint fk_branch_user
--         foreign key (user_id) references users (id)
-- );
-- create table categories
-- (
--     id serial primary key,
--     category_name varchar(50)
-- );
--
-- create table medicines
-- (
--     id            serial primary key,
--     medicine_name varchar(50) not null,
--     branch_id     int         not null,
--     quantity      integer     not null,
--     price         float       not null,
--     category_id   int         not null,
--     dosage        varchar(50) not null,
--     created_date  date,
--     constraint fk_medicine_branch
--         foreign key (branch_id) references branches (id),
--     constraint fk_medicine_category
--         foreign key (category_id) references categories (id)
-- );
--
-- create table baskets
-- (
--     id          serial primary key,
--     user_id     int,
--     medicine_id int,
--     quantity    int,
--     created_at  date,
--     constraint fk_medicine_user
--         foreign key (user_id) references users (id),
--     constraint fk_medicine_medicine
--         foreign key (medicine_id) references medicines (id)
-- );
-- create table ratings
-- (
--     id          serial primary key,
--     user_id     int,
--     medicine_id int,
--     rating      int,
--     feedback    varchar,
--     created_at  date,
--     constraint fk_ratings_users
--         foreign key (user_id) references users (id),
--     constraint fk_ratings_medicines
--         foreign key (medicine_id) references medicines (id)
-- );