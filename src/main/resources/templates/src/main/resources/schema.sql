create table USER_RECORD (
    id INT AUTO_INCREMENT PRIMARY KEY,
    last_name varchar(100) not null,
    first_name varchar(100) not null,
    email varchar(100),
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    CONSTRAINT unique_user_email UNIQUE (email)
);