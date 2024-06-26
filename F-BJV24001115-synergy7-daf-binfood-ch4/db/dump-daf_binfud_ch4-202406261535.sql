--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1
-- Dumped by pg_dump version 16.1

-- Started on 2024-06-26 15:35:01

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

DROP DATABASE daf_binfud_ch4;
--
-- TOC entry 4871 (class 1262 OID 42119)
-- Name: daf_binfud_ch4; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE daf_binfud_ch4 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';


\connect daf_binfud_ch4

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

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA public;


--
-- TOC entry 4872 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 42172)
-- Name: merchant; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.merchant (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone NOT NULL,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone NOT NULL,
    deleted boolean NOT NULL,
    merchant_location character varying(255),
    merchant_name character varying(255),
    open boolean NOT NULL
);


--
-- TOC entry 216 (class 1259 OID 42179)
-- Name: order_details; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.order_details (
    id uuid NOT NULL,
    deleted boolean NOT NULL,
    quantity integer NOT NULL,
    total_price bigint,
    order_id uuid,
    product_id uuid
);


--
-- TOC entry 217 (class 1259 OID 42184)
-- Name: orders; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.orders (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone NOT NULL,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone NOT NULL,
    completed boolean NOT NULL,
    deleted boolean NOT NULL,
    destination_address character varying(255),
    order_time timestamp(6) without time zone,
    user_id uuid
);


--
-- TOC entry 218 (class 1259 OID 42189)
-- Name: products; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.products (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone NOT NULL,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone NOT NULL,
    category character varying(255),
    deleted boolean NOT NULL,
    price bigint,
    product_name character varying(255),
    merchant_id uuid,
    CONSTRAINT products_category_check CHECK (((category)::text = ANY ((ARRAY['FOODS'::character varying, 'BAVERAGES'::character varying, 'OTHERS'::character varying])::text[])))
);


--
-- TOC entry 219 (class 1259 OID 42197)
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone NOT NULL,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone NOT NULL,
    deleted boolean NOT NULL,
    email_address character varying(255),
    password character varying(255),
    username character varying(255)
);


--
-- TOC entry 4861 (class 0 OID 42172)
-- Dependencies: 215
-- Data for Name: merchant; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4862 (class 0 OID 42179)
-- Dependencies: 216
-- Data for Name: order_details; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4863 (class 0 OID 42184)
-- Dependencies: 217
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.orders VALUES ('fcea29dd-ff79-4ed1-ab4b-8576bdbd4775', '2024-06-26 15:28:35.243', NULL, '2024-06-26 15:28:35.243', false, false, 'bogor', '2024-06-26 15:28:35.241064', 'd1c0ff11-3301-471a-b3c6-24b37966fcc0');
INSERT INTO public.orders VALUES ('0f851c84-3d5b-4164-9e55-110282806c82', '2024-06-26 15:33:26.677', NULL, '2024-06-26 15:34:39.261', true, false, 'bogor kota', '2024-06-26 15:33:26.653132', 'd1c0ff11-3301-471a-b3c6-24b37966fcc0');


--
-- TOC entry 4864 (class 0 OID 42189)
-- Dependencies: 218
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4865 (class 0 OID 42197)
-- Dependencies: 219
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.users VALUES ('d1c0ff11-3301-471a-b3c6-24b37966fcc0', '2024-06-26 15:27:30.762', NULL, '2024-06-26 15:27:30.762', false, 'user1@mail.com', 'user1password', 'user1');


--
-- TOC entry 4705 (class 2606 OID 42178)
-- Name: merchant merchant_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.merchant
    ADD CONSTRAINT merchant_pkey PRIMARY KEY (id);


--
-- TOC entry 4707 (class 2606 OID 42183)
-- Name: order_details order_details_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_details
    ADD CONSTRAINT order_details_pkey PRIMARY KEY (id);


--
-- TOC entry 4709 (class 2606 OID 42188)
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- TOC entry 4711 (class 2606 OID 42196)
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- TOC entry 4713 (class 2606 OID 42203)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4717 (class 2606 OID 42219)
-- Name: products fk1vfvb4v7idlb609b0tn6n7u4f; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT fk1vfvb4v7idlb609b0tn6n7u4f FOREIGN KEY (merchant_id) REFERENCES public.merchant(id);


--
-- TOC entry 4716 (class 2606 OID 42214)
-- Name: orders fk32ql8ubntj5uh44ph9659tiih; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk32ql8ubntj5uh44ph9659tiih FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4714 (class 2606 OID 42209)
-- Name: order_details fk4q98utpd73imf4yhttm3w0eax; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_details
    ADD CONSTRAINT fk4q98utpd73imf4yhttm3w0eax FOREIGN KEY (product_id) REFERENCES public.products(id);


--
-- TOC entry 4715 (class 2606 OID 42204)
-- Name: order_details fkjyu2qbqt8gnvno9oe9j2s2ldk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_details
    ADD CONSTRAINT fkjyu2qbqt8gnvno9oe9j2s2ldk FOREIGN KEY (order_id) REFERENCES public.orders(id);


-- Completed on 2024-06-26 15:35:01

--
-- PostgreSQL database dump complete
--

