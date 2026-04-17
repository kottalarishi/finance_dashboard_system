alter table  transactions
drop constraint fk_users_transactions;
     alter table transactions
     add constraint fk_users_transactions
     foreign key (user_id) references users(id)
     on delete cascade ;