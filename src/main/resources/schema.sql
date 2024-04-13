create table vacancy
(
    id           bigserial
        constraint vacancy_pk
            primary key,
    name         text not null,
    description  text not null,
    requirements text not null
);

alter table vacancy
    owner to postgres;

create table users
(
    id       bigserial
        constraint users_pk
            primary key,
    username text not null,
    password text not null,
    nickname text not null,
    role     text not null
);

alter table users
    owner to postgres;

create sequence public.vacancy_id_seq;

alter sequence public.vacancy_id_seq owner to postgres;

alter sequence public.vacancy_id_seq owned by public.vacancy.id;

create sequence public.users_id_seq;

alter sequence public.users_id_seq owner to postgres;

alter sequence public.users_id_seq owned by public.users.id;

