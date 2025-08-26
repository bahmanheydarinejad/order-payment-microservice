create table if not exists payments
(
    id
    uuid
    primary
    key,
    order_id
    uuid
    not
    null,
    amount
    numeric
(
    19,
    2
) not null,
    status varchar
(
    16
) not null,
    created_at timestamp with time zone not null,
                             failure_reason varchar (256)
    );
create index if not exists idx_payment_order on payments(order_id);
