CREATE SCHEMA IF NOT EXISTS project;

create table if not exists project.province
(
    id serial primary key,
    name varchar
);
