CREATE TABLE routes
(
  id serial NOT NULL,
  description character varying(255) NOT NULL,
  username character varying(11) NOT NULL,
  geom geometry NOT NULL,
  CONSTRAINT routes_pk PRIMARY KEY (id)
);
