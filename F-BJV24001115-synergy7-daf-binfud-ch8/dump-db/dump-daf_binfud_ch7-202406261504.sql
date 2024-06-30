--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1
-- Dumped by pg_dump version 16.1

-- Started on 2024-06-26 15:04:41

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

DROP DATABASE daf_binfud_ch7;
--
-- TOC entry 4896 (class 1262 OID 33622)
-- Name: daf_binfud_ch7; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE daf_binfud_ch7 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';


\connect daf_binfud_ch7

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
-- TOC entry 4897 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 33927)
-- Name: merchant; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.merchant (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone NOT NULL,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone NOT NULL,
    deleted boolean NOT NULL,
    is_open boolean,
    merchant_location character varying(255),
    merchant_name character varying(255)
);


--
-- TOC entry 216 (class 1259 OID 33934)
-- Name: order_detail; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.order_detail (
    id uuid NOT NULL,
    deleted boolean NOT NULL,
    quantity integer NOT NULL,
    total_price bigint,
    order_id uuid,
    product_id uuid
);


--
-- TOC entry 217 (class 1259 OID 33939)
-- Name: orders; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.orders (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone NOT NULL,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone NOT NULL,
    deleted boolean NOT NULL,
    destination_address character varying(255),
    is_completed boolean,
    order_time timestamp(6) without time zone,
    user_id uuid
);


--
-- TOC entry 218 (class 1259 OID 33944)
-- Name: product; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone NOT NULL,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone NOT NULL,
    category character varying(255),
    deleted boolean NOT NULL,
    product_name character varying(255),
    price bigint,
    merchant_id uuid,
    CONSTRAINT product_category_check CHECK (((category)::text = ANY ((ARRAY['FOODS'::character varying, 'BAVERAGES'::character varying])::text[])))
);


--
-- TOC entry 219 (class 1259 OID 33952)
-- Name: roles; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.roles (
    id uuid NOT NULL,
    role_name character varying(255),
    CONSTRAINT roles_role_name_check CHECK (((role_name)::text = ANY ((ARRAY['ROLE_USER'::character varying, 'ROLE_MERCHANT'::character varying])::text[])))
);


--
-- TOC entry 220 (class 1259 OID 33958)
-- Name: token; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.token (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone NOT NULL,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone NOT NULL,
    expiry_date timestamp(6) with time zone,
    token character varying(255),
    user_id uuid
);


--
-- TOC entry 221 (class 1259 OID 33963)
-- Name: user_roles; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.user_roles (
    user_id uuid NOT NULL,
    role_id uuid NOT NULL
);


--
-- TOC entry 222 (class 1259 OID 33968)
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone NOT NULL,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone NOT NULL,
    deleted boolean NOT NULL,
    email_address character varying(255),
    is_verified boolean,
    otp character varying(255),
    otp_expiration_time timestamp(6) without time zone,
    password character varying(255),
    username character varying(255)
);


--
-- TOC entry 4883 (class 0 OID 33927)
-- Dependencies: 215
-- Data for Name: merchant; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.merchant VALUES ('8e357a6a-a0ca-49d7-a48b-7053ab545fe3', '2024-06-26 02:11:11.689', NULL, '2024-06-26 02:11:11.689', false, true, 'BinarFud Merchant Jakarta', 'BinarFud Merchant Official');


--
-- TOC entry 4884 (class 0 OID 33934)
-- Dependencies: 216
-- Data for Name: order_detail; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.order_detail VALUES ('60838176-19ce-449c-ab7a-c018b9b299f9', false, 2, 30000, '439ceb40-74a1-4bbe-993f-3af7a6d9d4a2', 'ac1c2538-33dd-484c-84ef-79d00c98f320');
INSERT INTO public.order_detail VALUES ('af7b830e-5de1-4562-92f5-8faa448f60ee', false, 1, 13000, '439ceb40-74a1-4bbe-993f-3af7a6d9d4a2', '091a1848-0d84-4bcd-8481-7ea3a0ce7db6');
INSERT INTO public.order_detail VALUES ('fd8b93dc-61f1-4143-b2ce-48d792c41b9a', false, 1, 18000, '439ceb40-74a1-4bbe-993f-3af7a6d9d4a2', '40d69c38-ee69-4363-982c-3cf697ee74a6');
INSERT INTO public.order_detail VALUES ('c40a2d53-cf00-4594-825b-e973a138a836', false, 4, 20000, '439ceb40-74a1-4bbe-993f-3af7a6d9d4a2', '63bf8e45-2588-4a05-8e50-bb47ea9079d5');


--
-- TOC entry 4885 (class 0 OID 33939)
-- Dependencies: 217
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.orders VALUES ('439ceb40-74a1-4bbe-993f-3af7a6d9d4a2', '2024-06-26 02:11:11.76', NULL, '2024-06-26 02:50:50.131', false, 'Destination Address', true, '2024-06-26 02:11:11.758182', '292c2c10-a8ab-49ce-8b21-ca6163295c95');


--
-- TOC entry 4886 (class 0 OID 33944)
-- Dependencies: 218
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.product VALUES ('ac1c2538-33dd-484c-84ef-79d00c98f320', '2024-06-26 02:11:11.699', NULL, '2024-06-26 02:11:11.699', 'FOODS', false, 'Nasi Goreng', 15000, '8e357a6a-a0ca-49d7-a48b-7053ab545fe3');
INSERT INTO public.product VALUES ('091a1848-0d84-4bcd-8481-7ea3a0ce7db6', '2024-06-26 02:11:11.703', NULL, '2024-06-26 02:11:11.703', 'FOODS', false, 'Mie Goreng', 13000, '8e357a6a-a0ca-49d7-a48b-7053ab545fe3');
INSERT INTO public.product VALUES ('40d69c38-ee69-4363-982c-3cf697ee74a6', '2024-06-26 02:11:11.704', NULL, '2024-06-26 02:11:11.704', 'FOODS', false, 'Nasi + Ayam', 18000, '8e357a6a-a0ca-49d7-a48b-7053ab545fe3');
INSERT INTO public.product VALUES ('c85ebf95-727e-4bc4-8f5c-d47fd16ea011', '2024-06-26 02:11:11.704', NULL, '2024-06-26 02:11:11.704', 'BAVERAGES', false, 'Es Teh Manis', 3000, '8e357a6a-a0ca-49d7-a48b-7053ab545fe3');
INSERT INTO public.product VALUES ('63bf8e45-2588-4a05-8e50-bb47ea9079d5', '2024-06-26 02:11:11.704', NULL, '2024-06-26 02:11:11.704', 'BAVERAGES', false, 'Es Jeruk', 5000, '8e357a6a-a0ca-49d7-a48b-7053ab545fe3');


--
-- TOC entry 4887 (class 0 OID 33952)
-- Dependencies: 219
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.roles VALUES ('7248ce31-838b-4cd2-ae10-9e27d68906fd', 'ROLE_USER');
INSERT INTO public.roles VALUES ('71d38ddc-8349-4384-8cb9-ba300c44b42a', 'ROLE_MERCHANT');


--
-- TOC entry 4888 (class 0 OID 33958)
-- Dependencies: 220
-- Data for Name: token; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.token VALUES ('f824eec0-75bb-4add-8af9-8c6744b58c23', '2024-06-26 02:35:10.22', NULL, '2024-06-26 02:35:10.22', '2024-06-27 02:35:10.195654+07', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZXJjaGFudCIsImlhdCI6MTcxOTM0NDExMCwiZXhwIjoxNzE5NDMwNTEwfQ.0Muqtz_pzvk0J5Uwz16kF866S_bqK6ypI9MzahnI7cxfZlsWR6DQ4RpZZBWhrG6-d7JaD4Tz6kCCWmEv1EH_9A', 'bf2fe16d-f8f9-4911-9e28-be7b4f88a090');


--
-- TOC entry 4889 (class 0 OID 33963)
-- Dependencies: 221
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.user_roles VALUES ('292c2c10-a8ab-49ce-8b21-ca6163295c95', '7248ce31-838b-4cd2-ae10-9e27d68906fd');
INSERT INTO public.user_roles VALUES ('bf2fe16d-f8f9-4911-9e28-be7b4f88a090', '71d38ddc-8349-4384-8cb9-ba300c44b42a');


--
-- TOC entry 4890 (class 0 OID 33968)
-- Dependencies: 222
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.users VALUES ('292c2c10-a8ab-49ce-8b21-ca6163295c95', '2024-06-26 02:11:11.549', NULL, '2024-06-26 02:11:11.549', false, 'sulthondaffakautsar@gmail.com', true, NULL, NULL, '$2a$10$EL7cjKPV8hiot5rlavAjyeAiDzD3QfhjcY.UnOzbJfuvVqfvR7iU.', 'user');
INSERT INTO public.users VALUES ('bf2fe16d-f8f9-4911-9e28-be7b4f88a090', '2024-06-26 02:11:11.685', NULL, '2024-06-26 02:11:11.685', false, 'merchantmail@mail.com', true, NULL, NULL, '$2a$10$XfmVlRt3orcAwcfdxynlGuPOTqedAabkgmJNGnZ7b294fMsHLfJPK', 'merchant');


--
-- TOC entry 4718 (class 2606 OID 33933)
-- Name: merchant merchant_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.merchant
    ADD CONSTRAINT merchant_pkey PRIMARY KEY (id);


--
-- TOC entry 4720 (class 2606 OID 33938)
-- Name: order_detail order_detail_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT order_detail_pkey PRIMARY KEY (id);


--
-- TOC entry 4722 (class 2606 OID 33943)
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- TOC entry 4724 (class 2606 OID 33951)
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- TOC entry 4726 (class 2606 OID 33957)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 4728 (class 2606 OID 33962)
-- Name: token token_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT token_pkey PRIMARY KEY (id);


--
-- TOC entry 4730 (class 2606 OID 33967)
-- Name: user_roles user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 4732 (class 2606 OID 33974)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4735 (class 2606 OID 33985)
-- Name: orders fk32ql8ubntj5uh44ph9659tiih; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk32ql8ubntj5uh44ph9659tiih FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4733 (class 2606 OID 33980)
-- Name: order_detail fkb8bg2bkty0oksa3wiq5mp5qnc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT fkb8bg2bkty0oksa3wiq5mp5qnc FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- TOC entry 4738 (class 2606 OID 34000)
-- Name: user_roles fkh8ciramu9cc9q3qcqiv4ue8a6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- TOC entry 4739 (class 2606 OID 34005)
-- Name: user_roles fkhfh9dx7w3ubf1co1vdev94g3f; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4737 (class 2606 OID 33995)
-- Name: token fkj8rfw4x0wjjyibfqq566j4qng; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT fkj8rfw4x0wjjyibfqq566j4qng FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4736 (class 2606 OID 33990)
-- Name: product fkk47qmalv2pg906heielteubu7; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fkk47qmalv2pg906heielteubu7 FOREIGN KEY (merchant_id) REFERENCES public.merchant(id);


--
-- TOC entry 4734 (class 2606 OID 33975)
-- Name: order_detail fkrws2q0si6oyd6il8gqe2aennc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT fkrws2q0si6oyd6il8gqe2aennc FOREIGN KEY (order_id) REFERENCES public.orders(id);


-- Completed on 2024-06-26 15:04:41

--
-- PostgreSQL database dump complete
--

