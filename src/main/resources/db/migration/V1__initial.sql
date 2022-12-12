create table person
(
    id                      uuid                 not null,
    login                   VARCHAR(255)         not null,
    first_name              VARCHAR(255)         not null,
    last_name               VARCHAR(255)         not null,
    password                VARCHAR(255)         not null,
    role                    VARCHAR(255)         not null,
    account_non_expired     boolean default true not null,
    account_non_locked      boolean default true not null,
    credentials_non_expired boolean default true not null,
    enabled                 boolean default true not null,
    primary key (id)
);

alter table person
    add constraint person_login_unique unique (login);