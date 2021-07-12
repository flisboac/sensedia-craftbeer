create sequence sq_beer;

create table beer (
    id integer not null default nextval('sq_beer'),
    name varchar(128) not null,
    ingredients varchar(512) not null,
    alcohol_content varchar(64) not null,
    price decimal(15,2) not null,
    category varchar(128) not null,
    created_at timestamp not null,
    updated_at timestamp,
    constraint pk_beer primary key (id)
 );
