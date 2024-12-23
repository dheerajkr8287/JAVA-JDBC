create database spark;
use spark;
create table students(
        st_id int ,
        st_name varchar(30),
email varchar(30),
phoneNo varchar(10)
);
insert into students(st_id,st_name,email,phoneNo) values
(11,"raja","raja@gmail.com","8390282038"),(12,"kaka","kaka@gmail.com","9993237779"),(13,"rohit","rohit@gmail.com","9833128383"),
        (14,"gullu","gullu@gmail.com","1234567890");

select*from students;
