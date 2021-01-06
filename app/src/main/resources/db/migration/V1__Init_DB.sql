CREATE EXTENSION pg_trgm;

create table search_data
(
	entity_id bigint not null,
	type varchar(10) not null,
	name varchar(255) not null,
	normalized_text varchar(255) not null,
	image varchar(255),
	constraint search_data_pkey
		primary key (entity_id, type)
);

CREATE INDEX idx_search_data_text ON search_data USING GIN (normalized_text gin_trgm_ops);


create table partner_price
(
	external_medicine_id int not null,
	external_pharmacy_id int not null,
	integration_id int not null,
	price int not null,
	quantity int not null,
	created_at timestamp default now() not null,
	updated_at timestamp default now() not null,
	constraint partner_price_pkey primary key (external_medicine_id, integration_id, external_pharmacy_id)
);

create UNLOGGED table medicine_price
(
    medicine_id int not null,
	city_id int not null,
	created_at timestamp default now() not null,
	updated_at timestamp default now() not null,
	price integer not null,
	quantity integer not null,
	constraint medicine_price_pkey primary key (medicine_id, city_id)
);




