CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY ,
    userName VARCHAR(100),
    userEmail VARCHAR(100),
    phoneNumber VARCHAR(15) unique ,
    role VARCHAR(20) DEFAULT 'VIEWER',
    password VARCHAR(100)
);

CREATE  TABLE  transactions(

    id BIGSERIAL PRIMARY KEY ,
    amount NUMERIC(10,2),
    type VARCHAR(100),
    category VARCHAR(100),
    date DATE,
    notes VARCHAR(200),
    user_id BIGINT,
    CONSTRAINT fk_users_transactions
    FOREIGN KEY (user_id)
   REFERENCES users(id)

)

