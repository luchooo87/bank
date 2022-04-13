--
-- PostgreSQL database dump
--

-- Dumped from database version 12.9 (Ubuntu 12.9-0ubuntu0.20.04.1)
-- Dumped by pg_dump version 12.9 (Ubuntu 12.9-0ubuntu0.20.04.1)

SET
statement_timeout = 0;
SET
lock_timeout = 0;
SET
idle_in_transaction_session_timeout = 0;
SET
client_encoding = 'UTF8';
SET
standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET
check_function_bodies = false;
SET
xmloption = content;
SET
client_min_messages = warning;
SET
row_security = off;

SET
default_tablespace = '';

SET
default_table_access_method = heap;

--
-- Name: account; Type: TABLE; Schema: public; Owner: luis
--
-- DROP DATABASE bank1;
--
-- TOC entry 3000 (class 1262 OID 49343)
-- Name: bank; Type: DATABASE; Schema: -; Owner: luis
--

CREATE TABLE public.account
(
    id              integer               NOT NULL,
    number          character varying(20) NOT NULL,
    account_type    character varying(20) NOT NULL,
    initial_balance numeric(10, 6)        NOT NULL,
    status          boolean               NOT NULL,
    client_id       integer               NOT NULL
);

--
-- Name: account_id_seq; Type: SEQUENCE; Schema: public; Owner: luis
--

CREATE SEQUENCE public.account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;

--
-- Name: account_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: luis
--

ALTER SEQUENCE public.account_id_seq OWNED BY public.account.id;


--
-- Name: account_movements; Type: TABLE; Schema: public; Owner: luis
--

CREATE TABLE public.account_movements
(
    id             integer                  NOT NULL,
    movement_date  timestamp with time zone NOT NULL,
    movement_type  character varying(10)    NOT NULL,
    movement_value numeric(10, 6),
    balance        numeric(10, 6)           NOT NULL,
    account_id     integer                  NOT NULL,
    status         boolean                  NOT NULL
);

--
-- Name: account_movements_id_seq; Type: SEQUENCE; Schema: public; Owner: luis
--

CREATE SEQUENCE public.account_movements_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


--
-- Name: account_movements_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: luis
--

ALTER SEQUENCE public.account_movements_id_seq OWNED BY public.account_movements.id;


--
-- Name: client; Type: TABLE; Schema: public; Owner: luis
--

CREATE TABLE public.client
(
    id       integer               NOT NULL,
    password character varying(50) NOT NULL,
    status   boolean
);

--
-- Name: person; Type: TABLE; Schema: public; Owner: luis
--

CREATE TABLE public.person
(
    id             integer                NOT NULL,
    full_name      character varying(100) NOT NULL,
    identification character varying(20),
    address        character varying(200),
    age            smallint,
    phone          character varying(20)
);

--
-- Name: person_id_seq; Type: SEQUENCE; Schema: public; Owner: luis
--

CREATE SEQUENCE public.person_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


--
-- Name: person_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: luis
--

ALTER SEQUENCE public.person_id_seq OWNED BY public.person.id;


--
-- Name: account id; Type: DEFAULT; Schema: public; Owner: luis
--

ALTER TABLE ONLY public.account ALTER COLUMN id SET DEFAULT nextval('public.account_id_seq'::regclass);


--
-- Name: account_movements id; Type: DEFAULT; Schema: public; Owner: luis
--

ALTER TABLE ONLY public.account_movements ALTER COLUMN id SET DEFAULT nextval('public.account_movements_id_seq'::regclass);


--
-- Name: person id; Type: DEFAULT; Schema: public; Owner: luis
--

ALTER TABLE ONLY public.person ALTER COLUMN id SET DEFAULT nextval('public.person_id_seq'::regclass);


--
-- Name: account_movements account_movements_pk; Type: CONSTRAINT; Schema: public; Owner: luis
--

ALTER TABLE ONLY public.account_movements
    ADD CONSTRAINT account_movements_pk PRIMARY KEY (id);


--
-- Name: account account_pk; Type: CONSTRAINT; Schema: public; Owner: luis
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_pk PRIMARY KEY (id);


--
-- Name: client client_pk; Type: CONSTRAINT; Schema: public; Owner: luis
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pk PRIMARY KEY (id);


--
-- Name: person person_pk; Type: CONSTRAINT; Schema: public; Owner: luis
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pk PRIMARY KEY (id);


--
-- Name: account_movements account_account_movements_fk; Type: FK CONSTRAINT; Schema: public; Owner: luis
--

ALTER TABLE ONLY public.account_movements
    ADD CONSTRAINT account_account_movements_fk FOREIGN KEY (account_id) REFERENCES public.account(id);


--
-- Name: account client_account_fk; Type: FK CONSTRAINT; Schema: public; Owner: luis
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT client_account_fk FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: client person_client_fk; Type: FK CONSTRAINT; Schema: public; Owner: luis
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT person_client_fk FOREIGN KEY (id) REFERENCES public.person(id);


--
-- PostgreSQL database dump complete
--
