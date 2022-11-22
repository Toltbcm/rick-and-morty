--liquibase formatted sql
--changeSet <postgres>:<add-external-id-movie-character-table>

ALTER TABLE public.movie_character ADD external_id bigint;

--rollback ALTER TABLE DROP COLUMN external_id
