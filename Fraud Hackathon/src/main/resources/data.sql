\c chat

DROP DATABASE IF EXISTS open_cup;
CREATE DATABASE open_cup;

\c open_cup

CREATE TABLE IF NOT EXISTS transactions (
    id TEXT PRIMARY KEY,
    transaction_date TIMESTAMP NOT NULL,
    card TEXT NOT NULL,
    account TEXT NOT NULL,
    account_valid_to TIMESTAMP NOT NULL,
    oper_type TEXT NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    oper_result TEXT NOT NULL,
    terminal TEXT NOT NULL,
    terminal_type TEXT NOT NULL,
    city TEXT NOT NULL,
    address TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
     id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY ,
     client TEXT NOT NULL,
     last_name TEXT NOT NULL,
     first_name TEXT NOT NULL,
     patronymic TEXT,
     date_of_birth TIMESTAMP NOT NULL,
     passport TEXT NOT NULL,
     passport_valid_to TIMESTAMP NOT NULL,
     phone TEXT,
     transaction_id TEXT REFERENCES transactions(id)
);