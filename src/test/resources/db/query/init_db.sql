CREATE
    EXTENSION IF NOT EXISTS "uuid-ossp";

insert into person (id,
                    login,
                    first_name,
                    last_name,
                    enabled,
                    account_non_expired,
                    account_non_locked,
                    credentials_non_expired,
                    password,
                    role)
values ('8b2c8422-7ae4-40dc-a44b-7c5fac0948ab',
        'login',
        'Vitalii',
        'Provotar',
        true,
        true,
        true,
        true,
        'admin',
        'ADMIN');