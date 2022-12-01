--liquibase formatted sql
--changeSet <postgres>:<add-external-id-movie-characters-table>

ALTER TABLE public.movie_characters ADD external_id bigint;

--rollback ALTER TABLE DROP COLUMN external_id
