create table quote(
    id int auto_increment not null primary key,
    symbol varchar(30),
    open_value decimal(5,2),
    close_value decimal(5,2),
    timestamp timestamp
);