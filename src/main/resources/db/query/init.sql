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
values (uuid_generate_v4(),
        'login1',
        'Raf',
        'Bogaert',
        true,
        true,
        true,
        true,
        'admin',
        'ADMIN');