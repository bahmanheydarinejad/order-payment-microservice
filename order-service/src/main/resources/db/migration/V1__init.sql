create table if not exists orders
(
    id
    uuid
    primary
    key,
    customer_id
    varchar
(
    64
) not null,
    amount numeric
(
    19,
    2
) not null,
    status varchar
(
    16
) not null,
    created_at timestamp with time zone not null
                             );
