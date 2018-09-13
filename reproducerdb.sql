--
-- PostgreSQL database dump
--

-- Dumped from database version 10.0
-- Dumped by pg_dump version 10.0

-- Started on 2018-09-13 04:41:17

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12924)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2815 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 197 (class 1259 OID 26689)
-- Name: arrayissue1; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE arrayissue1 (
    id bigint NOT NULL,
    text character varying(255) NOT NULL,
    "array" bigint[] NOT NULL
);


ALTER TABLE arrayissue1 OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 26687)
-- Name: arrayissue1_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE arrayissue1_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE arrayissue1_id_seq OWNER TO postgres;

--
-- TOC entry 2816 (class 0 OID 0)
-- Dependencies: 196
-- Name: arrayissue1_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE arrayissue1_id_seq OWNED BY arrayissue1.id;


--
-- TOC entry 199 (class 1259 OID 26716)
-- Name: arrayissue2; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE arrayissue2 (
    id bigint NOT NULL,
    "array" bigint[],
    text character varying(255) NOT NULL
);


ALTER TABLE arrayissue2 OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 26714)
-- Name: arrayissue2_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE arrayissue2_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE arrayissue2_id_seq OWNER TO postgres;

--
-- TOC entry 2817 (class 0 OID 0)
-- Dependencies: 198
-- Name: arrayissue2_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE arrayissue2_id_seq OWNED BY arrayissue2.id;


--
-- TOC entry 2678 (class 2604 OID 26692)
-- Name: arrayissue1 id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY arrayissue1 ALTER COLUMN id SET DEFAULT nextval('arrayissue1_id_seq'::regclass);


--
-- TOC entry 2679 (class 2604 OID 26719)
-- Name: arrayissue2 id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY arrayissue2 ALTER COLUMN id SET DEFAULT nextval('arrayissue2_id_seq'::regclass);


--
-- TOC entry 2806 (class 0 OID 26689)
-- Dependencies: 197
-- Data for Name: arrayissue1; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY arrayissue1 (id, text, "array") FROM stdin;
1	test	{}
\.


--
-- TOC entry 2808 (class 0 OID 26716)
-- Dependencies: 199
-- Data for Name: arrayissue2; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY arrayissue2 (id, "array", text) FROM stdin;
1	\N	test
\.


--
-- TOC entry 2818 (class 0 OID 0)
-- Dependencies: 196
-- Name: arrayissue1_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('arrayissue1_id_seq', 1, false);


--
-- TOC entry 2819 (class 0 OID 0)
-- Dependencies: 198
-- Name: arrayissue2_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('arrayissue2_id_seq', 1, false);


--
-- TOC entry 2681 (class 2606 OID 26697)
-- Name: arrayissue1 arrayissue1_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY arrayissue1
    ADD CONSTRAINT arrayissue1_pkey PRIMARY KEY (id);


--
-- TOC entry 2683 (class 2606 OID 26724)
-- Name: arrayissue2 arrayissue3_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY arrayissue2
    ADD CONSTRAINT arrayissue3_pkey PRIMARY KEY (id);


-- Completed on 2018-09-13 04:41:17

--
-- PostgreSQL database dump complete
--

