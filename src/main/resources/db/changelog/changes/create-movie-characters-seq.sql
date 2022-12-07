--liquibase formatted sql
--changeSet <postgres>:<create-movie-characters-sequence-id>

CREATE SEQUENCE IF NOT EXISTS public.movie_characters_id_seq INCREMENT 1 START 1 MINVALUE 1;

--back DROP SEQUENCE public.movie_characters_id_seq;
