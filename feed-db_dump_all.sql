--
-- PostgreSQL database dump
--

-- Dumped from database version 13.1
-- Dumped by pg_dump version 13.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: feed; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.feed (
    id bigint NOT NULL,
    deleted boolean NOT NULL,
    description character varying(255),
    name character varying(255),
    url character varying(255)
);


ALTER TABLE public.feed OWNER TO root;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO root;

--
-- Data for Name: feed; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.feed (id, deleted, description, name, url) FROM stdin;
1	f	Dutch online news feed	NOS Nieuws	http://feeds.nos.nl/nosjournaal?format=xml
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.hibernate_sequence', 1, true);


--
-- Name: feed feed_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.feed
    ADD CONSTRAINT feed_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

